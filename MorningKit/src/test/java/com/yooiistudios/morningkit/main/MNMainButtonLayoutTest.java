package com.yooiistudios.morningkit.main;

import android.content.res.Configuration;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.yooiistudios.morningkit.common.RobolectricGradleTestRunner;
import com.yooiistudios.morningkit.main.admob.AdWebViewShadow;
import com.yooiistudios.morningkit.main.layout.MNMainLayoutSetter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by StevenKim on 2013. 11. 4..
 *
 * MNMainButtonLayoutTest
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config (shadows = { AdWebViewShadow.class }, emulateSdk = 18)
public class MNMainButtonLayoutTest {

    MNMainActivity mainActivity;

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;

        // visible() 이 뷰를 띄울 수 있게 해주는 중요한 메서드
        // 로그 관련이라는데 아직 잘 모르겠다.
//        System.setProperty("robolectric.logging", "stdout");
//        ShadowLog.stream = System.out;

//        mainActivity = Robolectric.buildActivity(MNMainActivity.class).create().start().resume().visible().get();
//        mainActivity = Robolectric.buildActivity(MNMainActivity.class).create().postResume().visible().get();
        mainActivity = Robolectric.buildActivity(MNMainActivity.class).create().visible().get();
    }


    /**
     * Button
     * @throws Exception
     */
    @Test
    @Config(qualifiers="port")
    public void checkButtonLayoutHeightOnPortrait() throws Exception {

        float expectedHeight = MNMainLayoutSetter.getButtonLayoutHeight(mainActivity, Configuration.ORIENTATION_PORTRAIT);

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(ViewGroup.LayoutParams.MATCH_PARENT, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((int)expectedHeight, View.MeasureSpec.EXACTLY);
        mainActivity.getButtonLayout().measure(widthMeasureSpec, heightMeasureSpec);

        Log.i("MNMainButtonLayoutTest", "port/height: " + expectedHeight);
        assertThat(mainActivity.getButtonLayout().getMeasuredHeight(), is((int) expectedHeight));
    }

    @Test
    @Config(qualifiers="large-1280x720-land")
    public void checkButtonLayoutHeightOnLandscape() throws Exception {

        // 1. 광고가 있는 경우: SMART_BANNER의 높이 + inner margin * 2가 되어야 한다.
        float expectedHeight = MNMainLayoutSetter.getButtonLayoutHeight(mainActivity, Configuration.ORIENTATION_LANDSCAPE);

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(ViewGroup.LayoutParams.MATCH_PARENT, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((int)expectedHeight, View.MeasureSpec.EXACTLY);
        mainActivity.getButtonLayout().measure(widthMeasureSpec, heightMeasureSpec);

        Log.i("MNMainButtonLayoutTest", "land/height: " + expectedHeight);
        assertThat(mainActivity.getButtonLayout().getMeasuredHeight(), is((int) expectedHeight));

        // 2. 광고가 없는 경우: main_button_layout_height
    }
}
