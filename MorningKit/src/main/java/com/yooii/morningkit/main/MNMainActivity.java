package com.yooii.morningkit.main;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.yooii.morningkit.MN;
import com.yooii.morningkit.R;
import com.yooii.morningkit.common.MNDeviceSizeChecker;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Views;

public class MNMainActivity extends Activity implements AdListener
{
    private static final String TAG = "MNMainActivity";

    @InjectView(R.id.main_widget_window_layout) MNWidgetWindowLayout mWidgetWindowLayout;
    @InjectView(R.id.main_alarm_list_view) MNMainAlarmListView mAlarmListView;
    @InjectView(R.id.main_button_layout) RelativeLayout mButtonLayout;
    @InjectView(R.id.main_admob_layout) RelativeLayout mAdmobLayout;
    @InjectView(R.id.adView) AdView mAdView;

    /**
     * Lifecycle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Activity start, Load the xml layout

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 기존의 레거시 코드 대신에 이 한줄로 findViewById를 모두 대체
        Views.inject(this);
//        mWidgetWindowView = (MNWidgetWindowLayout) findViewById(R.id.main_widget_window_view);
//        mAlarmListView = (MNMainAlarmListView) findViewById(R.id.main_alarm_list_view);
//        mRelativeLayout = (RelativeLayout) findViewById(R.id.main_button_layout);
//        mAdmobLayout = (RelativeLayout) findViewById(R.id.main_admob_layout);

        // 위젯 윈도우
        mWidgetWindowLayout.initWithWidgetMatrix();

        // 알람

        // 애드몹
//        mAdView = new AdView(this, AdSize.BANNER, MN.ads.ADMOB_ID);
//        mAdmobLayout.addView(mAdView);
//        mAdView.loadAd(new AdRequest());

        // 최초 실행시는 회전 감지를 안하기에, 명시적으로 최초 한번은 해줌
        onConfigurationChanged(getResources().getConfiguration());
    }

    @Override
    protected void onStart()
    {
        // Activity visible to user

        super.onStart();
    }

    @Override
    protected void onResume()
    {
        // Activity visible to user

        super.onResume();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
    }

    @Override
    protected void onPause()
    {
        // Partially visible

        super.onPause();
    }

    @Override
    protected void onStop()
    {
        // Activity no longer visible

        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        // Acitivity is destroyed
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    /**
     * Rotation
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_PORTRAIT: {
                // 위젯
                LinearLayout.LayoutParams widgetWindowLayoutParams = (LinearLayout.LayoutParams) mWidgetWindowLayout.getLayoutParams();
                widgetWindowLayoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
                float widgetWindowHeight = getResources().getDimension(R.dimen.widget_height) * 2
                        + getResources().getDimension(R.dimen.margin_outer)
                        + getResources().getDimension(R.dimen.margin_outer)
                        + getResources().getDimension(R.dimen.margin_inner);
                widgetWindowLayoutParams.height = (int)widgetWindowHeight;
                mWidgetWindowLayout.setLayoutParams(widgetWindowLayoutParams);

                // 로그 테스트
//                mWidgetWindowLayout.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.i(TAG, "widgetWindowLayout height:" + mWidgetWindowLayout.getHeight());
//                    }
//                });

                // 알람
                LinearLayout.LayoutParams alarmListViewLayoutParams = (LinearLayout.LayoutParams) mAlarmListView.getLayoutParams();
                alarmListViewLayoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
                float alarmListViewHeight = MNDeviceSizeChecker.getDeviceHeight(this) - widgetWindowHeight;
                alarmListViewLayoutParams.height = (int)alarmListViewHeight;
                mAlarmListView.setLayoutParams(alarmListViewLayoutParams);

                // 버튼
                RelativeLayout.LayoutParams buttonLayoutParams = (RelativeLayout.LayoutParams) mButtonLayout.getLayoutParams();
                buttonLayoutParams.height = (int)getResources().getDimension(R.dimen.main_button_layout_height);
                mButtonLayout.setLayoutParams(buttonLayoutParams);

                // 애드몹 레이아웃
                RelativeLayout.LayoutParams admobLayoutParams = (RelativeLayout.LayoutParams) mAdmobLayout.getLayoutParams();
                admobLayoutParams.height = (int)getResources().getDimension(R.dimen.main_admob_layout_height);
                mAdmobLayout.setLayoutParams(admobLayoutParams);

                // 애드몹
                // 버튼 레이아웃에 광고가 있을 경우 애드몹 레이아웃으로 옮기기
                if (mButtonLayout.findViewById(R.id.adView) != null) {
                    mButtonLayout.removeView(mAdView);
                    mAdmobLayout.addView(mAdView);
                }
                break;
            }
            case Configuration.ORIENTATION_LANDSCAPE: {

                // 애드몹 레이아웃
                RelativeLayout.LayoutParams admobLayoutParams = (RelativeLayout.LayoutParams) mAdmobLayout.getLayoutParams();
                admobLayoutParams.height = 0;
                mAdmobLayout.setLayoutParams(admobLayoutParams);

                // 버튼
                RelativeLayout.LayoutParams buttonLayoutParams = (RelativeLayout.LayoutParams) mButtonLayout.getLayoutParams();
                buttonLayoutParams.height =
                        (int)(getResources().getDimension(R.dimen.main_button_layout_height) + getResources().getDimension(R.dimen.margin_outer)*2);
                mButtonLayout.setLayoutParams(buttonLayoutParams);

                // 애드몹
                // Landscape 모드에서 버튼 레이아웃으로 광고 옮기기
                Log.i(TAG, mAdView.getRootView().toString());
                if (mAdmobLayout.findViewById(R.id.adView) != null) {
                    mAdmobLayout.removeView(mAdView);
                    mButtonLayout.addView(mAdView);
                    Log.i(TAG, mAdView.getRootView().toString());
                }
                if (mAdmobLayout.findViewById(R.id.adView) != null) {
                    Log.i(TAG, "adview is in admob Layout");
                }else{
                    Log.i(TAG, "adview is in button Layout");
                }
                break;
            }
        }

//        Log.i(TAG, "widgetWindowLayout height:" + mWidgetWindowLayout.getHeight());
//        Log.i(TAG, "alarmListView height:" + mAlarmListView.getHeight());
//        Log.i(TAG, "buttonLayout height:" + mButtonLayout.getHeight());
//        Log.i(TAG, "admobLayout height:" + mAdmobLayout.getHeight());
    }

    /**
     * OnClick
     */
    @OnClick(R.id.main_refresh_image) void refreshButtonClicked() {
        Log.i(TAG, "refreshButtonClicked");
    }

    @OnClick(R.id.main_configure_image) void configureButtonClicked() {
        Log.i(TAG, "configureButtonClicked");
    }

    /**
     * Getter
     */
    public RelativeLayout getButtonLayout() { return mButtonLayout; }
    public RelativeLayout getAdmobLayout() { return mAdmobLayout; }
    public MNWidgetWindowLayout getWidgetWindowLayout() {
        return mWidgetWindowLayout;
    }
    public MNMainAlarmListView getAlarmListView() { return mAlarmListView; }
    public AdView getAdView() { return mAdView; }

    /**
     * Admob
     */
    @Override
    public void onDismissScreen(Ad arg0) {
    }

    @Override
    public void onFailedToReceiveAd(Ad arg0, AdRequest.ErrorCode arg1) {
        // Log.i(TAG, "failed to receive ad (" + arg1 + ")");
    }

    @Override
    public void onLeaveApplication(Ad arg0) {
    }

    @Override
    public void onPresentScreen(Ad arg0) {
    }

    @Override
    public void onReceiveAd(Ad arg0) {
    }
}
