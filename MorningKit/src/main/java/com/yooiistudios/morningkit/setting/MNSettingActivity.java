package com.yooiistudios.morningkit.setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.yooiistudios.morningkit.MNApplication;
import com.yooiistudios.morningkit.MNIabInfo;
import com.yooiistudios.morningkit.R;
import com.yooiistudios.morningkit.common.analytic.MNAnalyticsUtils;
import com.yooiistudios.morningkit.common.locale.MNLocaleUtils;
import com.yooiistudios.morningkit.common.log.MNFlurry;
import com.yooiistudios.morningkit.common.sound.MNSoundEffectsPlayer;
import com.yooiistudios.morningkit.common.textview.CustomStyleTextView;
import com.yooiistudios.morningkit.setting.panel.MNPanelSettingFragment;
import com.yooiistudios.morningkit.setting.store.MNStoreFragment;
import com.yooiistudios.morningkit.setting.store.MNStoreType;
import com.yooiistudios.morningkit.setting.store.util.IabHelper;
import com.yooiistudios.morningkit.setting.theme.soundeffect.MNSound;

import lombok.Setter;

/**
 * Created by StevenKim in Morning Kit from Yooii Studios Co., LTD. on 2014. 1. 6.
 *
 * MNSettingActivity
 *  세팅 액티비티: 액션바가 바탕이 되고 안드로이드 기본 코드를 이용해 구현
 */
public class MNSettingActivity extends ActionBarActivity implements ActionBar.TabListener {
    private static final String TAG = "SettingActivity";
    private static final String SETTING_PREFERENCES = "SETTING_PREFERENCES";
    private static final String LATEST_TAB_SELECTION= "LATEST_TAB_SELECTION";
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    MNSettingSectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link android.support.v4.view.ViewPager} that will host the section contents.
     */
    MNSettingViewPager mViewPager;

    @Setter IabHelper iabHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 회전마다 Locale 을 새로 적용해줌(언어가 바뀌어 버리는 문제 해결)
        MNLocaleUtils.updateLocale(this);

        super.onCreate(savedInstanceState);

        // OS에 의해서 kill 당할 경우 복구하지 말고 메인 액티비티를 새로 띄워줌 - panelObject 와 관련된 오류 해결
        if (savedInstanceState != null) {
            finish();
            return;
        }

        setContentView(R.layout.activity_setting);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.icon_actionbar_morning);

        int latestTabIndex;
        SharedPreferences prefs = getSharedPreferences(SETTING_PREFERENCES, MODE_PRIVATE);
        if (prefs != null) {
            latestTabIndex = prefs.getInt(LATEST_TAB_SELECTION, 0);
        } else {
            latestTabIndex = 0;
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new MNSettingSectionsPagerAdapter(getSupportFragmentManager(), this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (MNSettingViewPager) findViewById(R.id.setting_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                final String name = "android:switcher:" + mViewPager.getId() + ":" + position;
                Fragment viewPagerFragment = getSupportFragmentManager().findFragmentByTag(name);

                if (position == 0) {
                    // 패널 탭일 경우 구매 확인을 한 번 더 해주자(락 아이템 구매 UI 처리용)
                    if (viewPagerFragment != null &&
                            viewPagerFragment instanceof MNPanelSettingFragment) {
                        viewPagerFragment.onResume();
                    }
                } else if (position == 1) {
                    // 상점 탭이고 네이버일 경우
                    boolean isStoreForNaver = MNIabInfo.STORE_TYPE.equals(MNStoreType.NAVER);
                    if (isStoreForNaver) {
                        // 1. 첫 진입인 경우에는 네이버 인앱 로딩
                        // 2. 로딩 후 탭 클릭 시는 구매 재로딩(다른 탭에서 언락했을 경우 UI 처리용)
                        // -> 2번 변경 -> 로딩 후 탭 클릭 시는 무조건 재로딩
                        if (viewPagerFragment == null) {
                            mViewPager.post(new Runnable() {
                                @Override
                                public void run() {
                                    Fragment viewPagerFragment = getSupportFragmentManager().findFragmentByTag(name);
                                    if (viewPagerFragment != null &&
                                            viewPagerFragment instanceof MNStoreFragment) {
                                        MNStoreFragment storeFragment = ((MNStoreFragment) viewPagerFragment);
                                        boolean isStoreForNaver = MNIabInfo.STORE_TYPE.equals(MNStoreType.NAVER);
                                        if (isStoreForNaver && !storeFragment.isNaverStoreStartLoading) {
                                            storeFragment.onFirstStoreLoading();
                                        }
                                    }
                                }
                            });
                        } else {
                            if (viewPagerFragment instanceof MNStoreFragment) {
                                MNStoreFragment storeFragment = ((MNStoreFragment)viewPagerFragment);
                                if (isStoreForNaver && !storeFragment.isNaverStoreStartLoading) {
                                    storeFragment.onFirstStoreLoading();
                                } else {
                                    // 2탭 너머 있는 상태에서 클릭할 경우에 onCreateView를 호출함
                                    if (storeFragment.getProductList() != null) {
                                        storeFragment.initUIAfterLoading(storeFragment.getProductList());
                                    } else {
                                        storeFragment.onFirstStoreLoading();
                                    }
                                }
                            }
                        }
                    }
                }
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            ActionBar.Tab tab = actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i))
                    .setTabListener(this);

            // 상점 탭일 경우 강조하는 기획 추가
            if (i == 1) {
                CustomStyleTextView customTextView = new CustomStyleTextView(MNSettingActivity.this,
                        R.attr.storeTabTextStyle);
                customTextView.setText(mSectionsPagerAdapter.getPageTitle(i));
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                customTextView.setLayoutParams(params);
                tab.setCustomView(customTextView);
            }
            actionBar.addTab(tab);

        }
        mViewPager.setCurrentItem(latestTabIndex);

        MNAnalyticsUtils.startAnalytics((MNApplication) getApplication(), TAG);
    }

    private void applyLocaledTabName() {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.action_bar_up_button_main);
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            if (i != 1) {
                actionBar.getTabAt(i).setText(mSectionsPagerAdapter.getPageTitle(i));
            } else {
                ((TextView) actionBar.getTabAt(i).getCustomView()).setText(
                        mSectionsPagerAdapter.getPageTitle(i));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        applyLocaledTabName();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!(iabHelper == null)) {
            if (!iabHelper.handleActivityResult(requestCode, resultCode, data)) {
                // not handled, so handle it ourselves (here's where you'd
                // perform any handling of activity results not related to in-app
                // billing...
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (MNSound.isSoundOn(this)) {
                MNSoundEffectsPlayer.play(R.raw.effect_view_close, this);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Catch the back button and make fragment animate
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            if (MNSound.isSoundOn(this)) {
                MNSoundEffectsPlayer.play(R.raw.effect_view_close, this);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * TabListener
     */
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());

        // remember the selection
        SharedPreferences prefs = getSharedPreferences(SETTING_PREFERENCES, MODE_PRIVATE);
        prefs.edit().putInt(LATEST_TAB_SELECTION, tab.getPosition()).apply();
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    protected void onStart() {
        // Activity visible to user
        super.onStart();
        FlurryAgent.onStartSession(this, MNFlurry.KEY);
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        // Activity no longer visible
        super.onStop();
        FlurryAgent.onEndSession(this);
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }
}
