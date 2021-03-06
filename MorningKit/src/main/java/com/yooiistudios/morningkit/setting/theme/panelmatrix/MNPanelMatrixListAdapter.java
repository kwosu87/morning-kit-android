package com.yooiistudios.morningkit.setting.theme.panelmatrix;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yooiistudios.morningkit.R;
import com.yooiistudios.morningkit.common.sound.MNSoundEffectsPlayer;
import com.yooiistudios.morningkit.setting.theme.MNSettingThemeDetailItemViewHolder;
import com.yooiistudios.morningkit.setting.theme.soundeffect.MNSound;

/**
 * Created by StevenKim in MNSettingActivityProject from Yooii Studios Co., LTD. on 2014. 1. 15.
 *
 * MNPanelMatrixListAdapter
 */
public class MNPanelMatrixListAdapter extends BaseAdapter {
    private Activity activity;

    private MNPanelMatrixListAdapter() {}
    public MNPanelMatrixListAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return MNPanelMatrixType.values().length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(activity).inflate(R.layout.setting_theme_detail_item, parent, false);

        if (convertView != null) {
            MNSettingThemeDetailItemViewHolder viewHolder = new MNSettingThemeDetailItemViewHolder(convertView);

            MNPanelMatrixType panelMatrixType = MNPanelMatrixType.valueOf(position);
            switch (panelMatrixType) {
                case PANEL_MATRIX_2X2:
                    viewHolder.getTitleTextView().setText("2 X 2");
                    break;

                case PANEL_MATRIX_2X3:
                    viewHolder.getTitleTextView().setText("2 X 3");
                    break;
            }

            // 2X3 유료 아이템 제거
            if (panelMatrixType != MNPanelMatrix.getCurrentPanelMatrixType(activity)) {
                viewHolder.getCheckImageView().setVisibility(View.GONE);
            }

            // theme
//            MNThemeType currentThemeType = MNTheme.getCurrentThemeType(activity);

            // onClick
            viewHolder.getInnerLayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MNSound.isSoundOn(activity)) {
                        MNSoundEffectsPlayer.play(R.raw.effect_view_close, activity);
                    }
                    MNPanelMatrix.setPanelMatrixType(MNPanelMatrixType.valueOf(position), activity);
                    activity.finish();
                }
            });

            // lock
            if (panelMatrixType == MNPanelMatrixType.PANEL_MATRIX_2X2) {
                viewHolder.getLockImageView().setVisibility(View.GONE);
            } else {
                viewHolder.getLockImageView().setVisibility(View.GONE);
                /*
                List<String> ownedSkus = SKIabProducts.loadOwnedIabProducts(activity);
                if (ownedSkus.contains(SKIabProducts.SKU_PANEL_MATRIX_2X3)) {
                    // 아이템 구매완료
                    viewHolder.getLockImageView().setVisibility(View.GONE);
                } else {
                    // 아이템 잠김
                    viewHolder.getInnerLayout().setBackgroundResource(
                            MNSettingResources.getLockItemResourcesId(currentThemeType));

                    // lock onClickListener
                    viewHolder.getInnerLayout().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 2*3은 Unlock이 아니라 상점에서 구매할 수 있게 한다
                            activity.startActivity(new Intent(activity, MNStoreActivity.class));
                            activity.overridePendingTransition(R.anim.activity_modal_up, R.anim.activity_hold);

                            // 플러리
                            Map<String, String> params = new HashMap<String, String>();
                            params.put(MNFlurry.CALLED_FROM, "Setting - Theme - 2X3");
                            FlurryAgent.logEvent(MNFlurry.STORE, params);
                        }
                    });
                }
                */
            }
        }
        return convertView;
    }
}
