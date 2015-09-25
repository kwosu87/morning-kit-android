package com.yooiistudios.morningkit.setting.store;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.yooiistudios.morningkit.MNIabInfo;
import com.yooiistudios.morningkit.R;
import com.yooiistudios.morningkit.common.log.MNFlurry;
import com.yooiistudios.morningkit.common.log.MNLog;
import com.yooiistudios.morningkit.common.number.MNDecimalFormatUtils;
import com.yooiistudios.morningkit.common.sound.MNSoundEffectsPlayer;
import com.yooiistudios.morningkit.iab.GoogleIabManager;
import com.yooiistudios.morningkit.iab.IabListener;
import com.yooiistudios.morningkit.iab.IabManager;
import com.yooiistudios.morningkit.iab.NaverIabInventoryItem;
import com.yooiistudios.morningkit.iab.NaverIabManager;
import com.yooiistudios.morningkit.panel.core.MNPanel;
import com.yooiistudios.morningkit.panel.core.MNPanelType;
import com.yooiistudios.morningkit.setting.MNSettingActivity;
import com.yooiistudios.morningkit.setting.store.iab.SKIabProducts;
import com.yooiistudios.morningkit.setting.theme.panelmatrix.MNPanelMatrix;
import com.yooiistudios.morningkit.setting.theme.panelmatrix.MNPanelMatrixType;
import com.yooiistudios.morningkit.setting.theme.soundeffect.MNSound;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by StevenKim in Morning Kit from Yooii Studios Co., LTD. on 2014. 1. 12.
 * <p/>
 * MNStoreFragment
 */
public class MNStoreFragment extends Fragment implements
        MNStoreGridViewAdapter.MNStoreGridViewOnClickListener, IabListener {
//    private static final String TAG = "MNStoreFragment";
    @Setter @Getter private IabManager iabManager;
    @Setter MNSettingActivity activity;

    @InjectView(R.id.setting_store_progressBar) ProgressBar progressBar;
    @InjectView(R.id.setting_store_loading_view) View loadingView;

    @InjectView(R.id.setting_store_full_version_title) TextView fullVersionTitleTextView;
    @InjectView(R.id.setting_store_full_version_description) TextView fullVersionDescriptionTextView;
    
    @InjectView(R.id.setting_store_full_version_image_view) ImageView fullVersionImageView;
    @InjectView(R.id.setting_store_full_version_button_imageview) ImageView fullVersionButtonImageView;
    @InjectView(R.id.setting_store_full_version_button_textview) TextView fullVersionButtonTextView;

    @InjectView(R.id.setting_store_tab_left_divider) View leftTabDivider;
    @InjectView(R.id.setting_store_tab_right_divider) View rightTabDivider;

    @InjectView(R.id.setting_store_tab_function_layout) RelativeLayout functionTabLayout;
    @InjectView(R.id.setting_store_tab_panel_layout) RelativeLayout panelTabLayout;
    @InjectView(R.id.setting_store_tab_theme_layout) RelativeLayout themeTabLayout;

    @InjectView(R.id.setting_store_tab_function_textview) TextView functionTextView;
    @InjectView(R.id.setting_store_tab_panel_textview) TextView  panelTextView;
    @InjectView(R.id.setting_store_tab_theme_textview) TextView  themeTextView;

    @InjectView(R.id.setting_store_function_gridview) GridView functionGridView;
    @InjectView(R.id.setting_store_panel_gridview) GridView panelGridView;
    @InjectView(R.id.setting_store_theme_gridview) GridView themeGridView;

    // For Test
    @InjectView(R.id.setting_store_reset_button) Button resetButton;
    @InjectView(R.id.setting_store_debug_button) Button debugButton;

    // For Naver
    public static final boolean IS_STORE_FOR_NAVER = false;
    public static final int RC_NAVER_IAB = 8374;
    public boolean isNaverStoreStartLoading = false;
//    public boolean isNaverStoreFinishLoading = false;
    @Setter boolean isFragmentForActivity = false;
    @Getter List<NaverIabInventoryItem> productList;

    // setActivity 반드시 해줘야함
    public MNStoreFragment(){}

    // getActivity()가 null일 경우를 대비
    Context context;

    // 이전에 생성된 프래그먼트를 유지
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRetainInstance(true); // 2탭 멀리 있을 때 새로 생성해 주어야 제대로 초기화가 진행
        setRetainInstance(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.setting_store_fragment, container, false);
        if (rootView != null) {
            ButterKnife.inject(this, rootView);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        showLoadingViews();

        onTabClicked(functionTabLayout);

        initIab();
        initUI();
        checkDebug();

        // 플러리
        Map<String, String> params = new HashMap<String, String>();
        params.put(MNFlurry.CALLED_FROM, "Setting - Store");
        FlurryAgent.logEvent(MNFlurry.STORE, params);
    }

    private void initIab() {
        fullVersionButtonTextView.setSelected(true);

        // 이 부분 때문에 크래시가 나서 일단 null 체크를 해줌
        if (MNIabInfo.STORE_TYPE.equals(MNStoreType.NAVER)) {
            // 네이버는 로딩을 탭 클릭 시로 미룸, 단 상점 액티비티는 처음에 로딩
            if (isFragmentForActivity) {
                onFirstStoreLoading();
            }
        } else {
            if (activity != null) {
                iabManager = new GoogleIabManager(activity, this);
                iabManager.setup();
            }
        }
    }

    private void initUI() {
        fullVersionDescriptionTextView.setSelected(true); // 넘칠 경우를 대비
        functionGridView.setAdapter(new MNStoreGridViewAdapter(getActivity(), MNStoreTabType.FUNCTIONS,
                null, this));
        panelGridView.setAdapter(new MNStoreGridViewAdapter(getActivity(), MNStoreTabType.PANELS,
                null, this));
        themeGridView.setAdapter(new MNStoreGridViewAdapter(getActivity(), MNStoreTabType.THEMES,
                null, this));
    }

    // For Google
    private void initUIAfterLoading(Map<String, String> googleSkuToPriceMap) {
        if (googleSkuToPriceMap.containsKey(SKIabProducts.SKU_FULL_VERSION)) {
            fullVersionButtonTextView.setText(R.string.store_purchased);
            fullVersionImageView.setClickable(false);
            fullVersionButtonImageView.setClickable(false);
        } else {
            if (!MNStoreDebugChecker.isUsingStore(getActivity())) {
                initFullVersionUIDebug();
            } else {
                // 특정 기기에서 184라인에서 loadAnimation 을 하는 도중 크래시.
                // NullpointerException 이고 원인은 정확히 모름
                try {
                    Animation animation = AnimationUtils.loadAnimation(getActivity(),
                            R.anim.store_view_scale_up_and_down);
                    if (animation != null) {
                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                Animation textAnimation = AnimationUtils.loadAnimation(getActivity(),
                                        R.anim.store_view_scale_up_and_down);
                                if (textAnimation != null) {
                                    fullVersionButtonTextView.startAnimation(textAnimation);
                                }
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });
                        fullVersionButtonImageView.startAnimation(animation);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String fullVersionPrice = googleSkuToPriceMap.get(SKIabProducts.SKU_FULL_VERSION);
                String fullVersionPriceMessage;
                if (MNIabInfo.STORE_TYPE.equals(MNStoreType.NAVER)) {
                    fullVersionPriceMessage = "₩" + MNDecimalFormatUtils.makeStringComma(fullVersionPrice);
                } else {
                    fullVersionPriceMessage = fullVersionPrice;
                }
                fullVersionButtonTextView.setText(fullVersionPriceMessage);
                fullVersionImageView.setClickable(true);
                fullVersionButtonImageView.setClickable(true);
            }
        }
        // Other
        ((MNStoreGridViewAdapter) functionGridView.getAdapter()).setGoogleSkuToPriceMap(googleSkuToPriceMap);
        ((MNStoreGridViewAdapter) panelGridView.getAdapter()).setGoogleSkuToPriceMap(googleSkuToPriceMap);
        ((MNStoreGridViewAdapter) themeGridView.getAdapter()).setGoogleSkuToPriceMap(googleSkuToPriceMap);

        // 특정 기기에서 getActivity()가 null일 경우를 대비한 처리
        List<String> ownedSkus = null;
        if (getActivity() != null) {
            ownedSkus = SKIabProducts.loadOwnedIabProducts(getActivity());
        } else {
            if (context != null) {
                ownedSkus = SKIabProducts.loadOwnedIabProducts(context);
            }
        }
        ((MNStoreGridViewAdapter) functionGridView.getAdapter()).setOwnedSkus(ownedSkus);
        ((MNStoreGridViewAdapter) panelGridView.getAdapter()).setOwnedSkus(ownedSkus);
        ((MNStoreGridViewAdapter) themeGridView.getAdapter()).setOwnedSkus(ownedSkus);

        ((MNStoreGridViewAdapter) functionGridView.getAdapter()).notifyDataSetChanged();
        ((MNStoreGridViewAdapter) panelGridView.getAdapter()).notifyDataSetChanged();
        ((MNStoreGridViewAdapter) themeGridView.getAdapter()).notifyDataSetChanged();
        hideLoadingViews();
    }

    private void checkDebug() {
        if (MNLog.isDebug) {
            resetButton.setVisibility(View.VISIBLE);
            debugButton.setVisibility(View.VISIBLE);
            if (MNStoreDebugChecker.isUsingStore(getActivity())) {
                if (MNIabInfo.STORE_TYPE.equals(MNStoreType.NAVER)) {
                    debugButton.setText("Naver Store");
                } else {
                    debugButton.setText("Google Store");
                }
            } else {
                debugButton.setText("Debug");
            }
        } else {
            resetButton.setVisibility(View.GONE);
            debugButton.setVisibility(View.GONE);
            MNStoreDebugChecker.setUsingStore(true, getActivity());
        }
    }

//    private void updateUIAfterPurchase(Purchase info) {
//        if (info.getSku().equals(SKIabProducts.SKU_FULL_VERSION)) {
//            // Full version
//            fullVersionButtonTextView.setText(R.string.store_purchased);
//            fullVersionImageView.setClickable(false);
//            fullVersionButtonImageView.setClickable(false);
//
//            // 풀버전 구매시 2X3으로 매트릭스 변경
//            apply2X3MatrixAfterFullVersionPurchase();
//        } else {
//            // Others
//            List<String> ownedSkus = SKIabProducts.loadOwnedIabProducts(getActivity());
//            ((MNStoreGridViewAdapter) functionGridView.getAdapter()).setOwnedSkus(ownedSkus);
//            ((MNStoreGridViewAdapter) panelGridView.getAdapter()).setOwnedSkus(ownedSkus);
//            ((MNStoreGridViewAdapter) themeGridView.getAdapter()).setOwnedSkus(ownedSkus);
//        }
//        ((MNStoreGridViewAdapter) functionGridView.getAdapter()).notifyDataSetChanged();
//        ((MNStoreGridViewAdapter) panelGridView.getAdapter()).notifyDataSetChanged();
//        ((MNStoreGridViewAdapter) themeGridView.getAdapter()).notifyDataSetChanged();
//    }

    public void onFirstStoreLoading() {
        showLoadingViews();

        iabManager = new NaverIabManager(activity, this);
    }

    public void onRefreshPurchases() {
        // 구매된 아이템들 UI 다시 확인
        List<String> ownedSkus = SKIabProducts.loadOwnedIabProducts(getActivity());
        if (ownedSkus.contains(SKIabProducts.SKU_FULL_VERSION)) {
            // Full version
            fullVersionButtonTextView.setText(R.string.store_purchased);
            fullVersionImageView.setClickable(false);
            fullVersionButtonImageView.setClickable(false);
        }
        ((MNStoreGridViewAdapter) functionGridView.getAdapter()).setOwnedSkus(ownedSkus);
        ((MNStoreGridViewAdapter) panelGridView.getAdapter()).setOwnedSkus(ownedSkus);
        ((MNStoreGridViewAdapter) themeGridView.getAdapter()).setOwnedSkus(ownedSkus);

        if (productList != null) {
            ((MNStoreGridViewAdapter) functionGridView.getAdapter()).setNaverIabInventoryItemList(productList);
            ((MNStoreGridViewAdapter) panelGridView.getAdapter()).setNaverIabInventoryItemList(productList);
            ((MNStoreGridViewAdapter) themeGridView.getAdapter()).setNaverIabInventoryItemList(productList);
        }

        ((MNStoreGridViewAdapter) functionGridView.getAdapter()).notifyDataSetChanged();
        ((MNStoreGridViewAdapter) panelGridView.getAdapter()).notifyDataSetChanged();
        ((MNStoreGridViewAdapter) themeGridView.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (iabManager != null) {
            iabManager.dispose();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fullVersionTitleTextView.setText(R.string.store_buy_full_version);
        fullVersionDescriptionTextView.setText(R.string.store_buy_full_version_description);

        functionTextView.setText(R.string.store_tab_functions);
        panelTextView.setText(R.string.store_tab_widgets);
        themeTextView.setText(R.string.store_tab_themes);

        // 풀버전 구매 버튼 때문에 한번 더 로딩을 요청, 그러면 아래 그리드뷰도 자동으로 언어가 적용
        if (!isFragmentForActivity) {
            initIab();
        }
    }

    /**
     * Loading
     */
    private void showLoadingViews() {
        if (progressBar != null) {
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }
        if (loadingView != null) {
            loadingView.setVisibility(View.VISIBLE);
        }
    }

    private void hideLoadingViews() {
        if (progressBar != null) {
            progressBar.setVisibility(ProgressBar.INVISIBLE);
        }
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }
    }

    /**
     * Tab onclick
     */
    @OnClick({ R.id.setting_store_tab_function_layout, R.id.setting_store_tab_panel_layout,
            R.id.setting_store_tab_theme_layout})
    public void onTabClicked(RelativeLayout tabLayout) {
        functionTabLayout.setBackgroundColor(getResources().getColor(R.color.setting_store_tab_unselected_color));
        panelTabLayout.setBackgroundColor(getResources().getColor(R.color.setting_store_tab_unselected_color));
        themeTabLayout.setBackgroundColor(getResources().getColor(R.color.setting_store_tab_unselected_color));

        functionTextView.setTextColor(getResources().getColor(R.color.setting_store_tab_unselected_text_color));
        panelTextView.setTextColor(getResources().getColor(R.color.setting_store_tab_unselected_text_color));
        themeTextView.setTextColor(getResources().getColor(R.color.setting_store_tab_unselected_text_color));

        functionGridView.setVisibility(View.GONE);
        panelGridView.setVisibility(View.GONE);
        themeGridView.setVisibility(View.GONE);

        leftTabDivider.setVisibility(View.GONE);
        rightTabDivider.setVisibility(View.GONE);

        if (tabLayout.equals(functionTabLayout)) {
            functionTabLayout.setBackgroundColor(getResources().getColor(R.color.setting_store_tab_selected_color));
            functionTextView.setTextColor(getResources().getColor(R.color.setting_store_tab_selected_text_color));
            functionGridView.setVisibility(View.VISIBLE);
            rightTabDivider.setVisibility(View.VISIBLE);
        } else if (tabLayout.equals(panelTabLayout)) {
            panelTabLayout.setBackgroundColor(getResources().getColor(R.color.setting_store_tab_selected_color));
            panelTextView.setTextColor(getResources().getColor(R.color.setting_store_tab_selected_text_color));
            panelGridView.setVisibility(View.VISIBLE);
        } else {
            themeTabLayout.setBackgroundColor(getResources().getColor(R.color.setting_store_tab_selected_color));
            themeTextView.setTextColor(getResources().getColor(R.color.setting_store_tab_selected_text_color));
            themeGridView.setVisibility(View.VISIBLE);
            leftTabDivider.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Full version button
     */
    @OnClick({ R.id.setting_store_full_version_image_view, R.id.setting_store_full_version_button_imageview})
    public void onFullVersionClicked(ImageView v) {
        if (MNSound.isSoundOn(getActivity())) {
            MNSoundEffectsPlayer.play(R.raw.effect_view_open, getActivity());
        }
        if (MNStoreDebugChecker.isUsingStore(getActivity())) {
            showLoadingViews();
            iabManager.purchase(SKIabProducts.SKU_FULL_VERSION);
        } else {
            SKIabProducts.saveIabProduct(SKIabProducts.SKU_FULL_VERSION, getActivity());
            initUI();
            initFullVersionUIDebug();
            // 디버그 모드도 풀버전 구매시 2X3으로 매트릭스 변경
            apply2X3MatrixAfterFullVersionPurchase();
        }
    }

    private void showComplain(String string) {
        /*
        AlertDialog.Builder bld = new AlertDialog.Builder(getActivity());
        bld.setMessage(string);
        bld.setNeutralButton(getString(R.string.ok), null);
        bld.create().show();
        */

        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
    }

    /**
     * Debug Test
     */
    @OnClick(R.id.setting_store_debug_button)
    void debugButtonClicked() {
        if (MNStoreDebugChecker.isUsingStore(getActivity())) {
            debugButton.setText("Debug");
            MNStoreDebugChecker.setUsingStore(false, getActivity());
        } else {
            if (MNIabInfo.STORE_TYPE.equals(MNStoreType.NAVER)) {
                debugButton.setText("Naver Store");
            } else {
                debugButton.setText("Google Store");
            }
            MNStoreDebugChecker.setUsingStore(true, getActivity());
        }
    }

    @OnClick(R.id.setting_store_reset_button)
    void resetButtonClicked() {
        // 디버그 상태에서 구매했던 아이템들을 리셋
        if (MNStoreDebugChecker.isUsingStore(getActivity())) {
            if (iabManager != null) {
                iabManager.setup();
            }
            initUI();
        } else {
            SKIabProducts.resetIabProductsDebug(getActivity());
            initUI();
            initFullVersionUIDebug();
        }
    }

    // 풀 버전을 제외한 아이템을 클릭하면 구매 처리하기

    /**
     * MNStoreGridViewOnClickListener
     */
    @Override
    public void onItemClickedDebug(String sku) {
        SKIabProducts.saveIabProduct(sku, getActivity());
        initUI();
    }

    private void initFullVersionUIDebug() {
        List<String> ownedSkus = SKIabProducts.loadOwnedIabProducts(getActivity());
        if (ownedSkus.contains(SKIabProducts.SKU_FULL_VERSION)) {
            fullVersionButtonTextView.setText(R.string.store_purchased);
            fullVersionImageView.setClickable(false);
            fullVersionButtonImageView.setClickable(false);
        } else {
            Animation animation = AnimationUtils.loadAnimation(getActivity(),
                    R.anim.store_view_scale_up_and_down);
            if (animation != null) {
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Animation textAniamtion = AnimationUtils.loadAnimation(getActivity(),
                                R.anim.store_view_scale_up_and_down);
                        if (textAniamtion != null) {
                            fullVersionButtonTextView.startAnimation(textAniamtion);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                fullVersionButtonImageView.startAnimation(animation);
            }
            fullVersionButtonTextView.setText("$1.99");
            fullVersionImageView.setClickable(true);
            fullVersionButtonImageView.setClickable(true);
        }
    }

    /**
     *  Naver App Store
     */
    @Override
    public void onItemClickedForNaver(String sku) {
        showLoadingViews();

        iabManager.purchase(sku);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // TODO: Naver IAB. 빌드 되게 하기 위해 주석 처리
            // TODO: Naver IAB. 이 클래스에서 이 부분만 처리가 안됨
            /*
            case RC_NAVER_IAB:
                if (resultCode == Activity.RESULT_OK) {
                    String action = data.getStringExtra(NaverIabActivity.KEY_ACTION);
                    if (action.equals(NaverIabActivity.ACTION_PURCHASE)) {
                        String purchasedIabItemKey = data.getStringExtra(NaverIabActivity.KEY_PRODUCT_KEY);

                        if (purchasedIabItemKey != null) {
                            // SKIabProducts에 적용
                            String ownedSku = NaverIabProductUtils.googleSkuMap.get(purchasedIabItemKey);
                            SKIabProducts.saveIabProduct(ownedSku, getActivity());

                            // 구매 후 UI 재로딩
                            updateUIAfterPurchase(ownedSku);
                        }

                    } else if (action.equals(NaverIabActivity.ACTION_QUERY_PURCHASE)) {
                        ArrayList<NaverIabInventoryItem> productList =
                                data.getParcelableArrayListExtra(NaverIabActivity.KEY_PRODUCT_LIST);

                        // 구매 목록 SKIabProducts에 적용
                        SKIabProducts.saveIabProducts(productList, getActivity());
                        this.productList = productList;

                        // 이후 UI 로딩
                        initUIAfterLoading(productList);
                    }
                }
                // 네이버 인앱 페이지에서 돌아오면 로딩뷰 감추기
                hideLoadingViews();
                break;
            */
        }
    }

    private void updateUIAfterPurchase(String ownedSku) {
        if (ownedSku.equals(SKIabProducts.SKU_FULL_VERSION)) {
            // Full version
            fullVersionButtonTextView.setText(R.string.store_purchased);
            fullVersionImageView.setClickable(false);
            fullVersionButtonImageView.setClickable(false);

            // 풀버전 구매시 2X3으로 매트릭스 변경
            apply2X3MatrixAfterFullVersionPurchase();
        }
        // Others
        List<String> ownedSkus = SKIabProducts.loadOwnedIabProducts(getActivity());
        ((MNStoreGridViewAdapter) functionGridView.getAdapter()).setOwnedSkus(ownedSkus);
        ((MNStoreGridViewAdapter) panelGridView.getAdapter()).setOwnedSkus(ownedSkus);
        ((MNStoreGridViewAdapter) themeGridView.getAdapter()).setOwnedSkus(ownedSkus);

        ((MNStoreGridViewAdapter) functionGridView.getAdapter()).notifyDataSetChanged();
        ((MNStoreGridViewAdapter) panelGridView.getAdapter()).notifyDataSetChanged();
        ((MNStoreGridViewAdapter) themeGridView.getAdapter()).notifyDataSetChanged();

        // 만약 로그인이나 로딩이 잘못되어 구매했을 때 까지 정보가 뜨지 않았다면 다시 가격 정보 로딩을 해 줄것
//        if (!isNaverStoreFinishLoading) {
//            onFirstStoreLoading();
//        }
    }

    private void apply2X3MatrixAfterFullVersionPurchase() {
        // 2X3을 기본으로 바꾸어 주고, 5, 6번째 패널을 날짜 계산, 사진첩 패널로 바꾸어주기
        MNPanelMatrix.setPanelMatrixType(MNPanelMatrixType.PANEL_MATRIX_2X3, getActivity());
        MNPanel.changeToEmptyDataPanel(getActivity(), MNPanelType.DATE_COUNTDOWN.getUniqueId(), 4);
        MNPanel.changeToEmptyDataPanel(getActivity(), MNPanelType.PHOTO_FRAME.getUniqueId(), 5);
    }

    // 특정 기기에서 getActivity()가 null일 경우 대비
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity != null) {
            context = activity;
        }
    }

    @Override
    public void onIabSetupFinished() { }

    @Override
    public void onIabSetupFailed(String message) {
        try {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        hideLoadingViews();
    }

    @Override
    public void onQueryFinished(Map<String, String> googleSkuToPriceMap) {
        // called after SKIabProducts.saveIabProducts(inv, activity)
        initUIAfterLoading(googleSkuToPriceMap);
        hideLoadingViews();
    }

    @Override
    public void onQueryFailed(String message) {
        try {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        hideLoadingViews();
    }

    @Override
    public void onIabPurchaseFinished(String googleSku) {
        SKIabProducts.saveIabProduct(googleSku, getActivity());
        updateUIAfterPurchase(googleSku);
    }

    @Override
    public void onIabPurchaseFailed(String message) {

    }
}
