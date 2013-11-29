package com.yooiistudios.morningkit.alarm.listview;

import android.view.View;

import com.yooiistudios.morningkit.R;
import com.yooiistudios.morningkit.alarm.model.MNAlarm;
import com.yooiistudios.morningkit.alarm.model.MNAlarmListManager;
import com.yooiistudios.morningkit.alarm.model.MNAlarmMaker;
import com.yooiistudios.morningkit.common.RobolectricGradleTestRunner;
import com.yooiistudios.morningkit.main.MNMainActivity;
import com.yooiistudios.morningkit.main.admob.AdWebViewShadow;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import java.util.Calendar;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
//import static org.junit.matchers.JUnitMatchers.*;

/**
 * Created by StevenKim on 2013. 11. 11..
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config (shadows = { AdWebViewShadow.class })
public class MNAlarmListViewTest {

    MNAlarm defaultAlarm;
    MNAlarm customAlarm;
    MNMainActivity mainActivity;
    MNAlarmListAdaptor alarmListAdaptor;

    @Before
    public void setUp() {
        mainActivity = Robolectric.buildActivity(MNMainActivity.class).create().visible().get();
        alarmListAdaptor = new MNAlarmListAdaptor(mainActivity.getBaseContext());
    }

    @Test
    public void alarmListAdaptorTest() {
        // MNAlarmListAdaptor의 초기화를 위해서 Context가 null이 아닌지 체크
        assertThat(mainActivity.getBaseContext(), notNullValue());
        // null 체크
        assertThat(alarmListAdaptor, notNullValue());

        // alarmListAdaptor의 뷰 카운트는 알람 갯수 + 1(알람 추가 뷰)
        assertThat(alarmListAdaptor.getCount(), is(MNAlarmListManager.getAlarmList(mainActivity.getBaseContext()).size() + 1));
    }

    @Test
    public void alarmListViewTest() {
        assertThat(mainActivity.getAlarmListView().getCount(), is(alarmListAdaptor.getCount()));

        // 무조건 알람이 한개는 있을 것이므로 첫번째 뷰에 넣어둔 태그인 MNAlarm의 인스턴스를 확인한다
        View firstAlarmItemView = mainActivity.getAlarmListView().getChildAt(0);
        if (firstAlarmItemView != null) {
            assertThat(firstAlarmItemView.getTag(), notNullValue());
            // instanceOf로 확인하는 것이 정석인 듯 하나 is()로도 확인이 가능한듯
//            assertThat(firstAlarmItemView.getTag(), instanceOf(MNAlarm.class));
            assertThat(firstAlarmItemView.getTag(), is(MNAlarm.class));
        }
        // 알람 추가 뷰의 태그를 확인한다
        View alarmCreateAlarmItemView =
                mainActivity.getAlarmListView().getChildAt(MNAlarmListManager.getAlarmList(mainActivity.getBaseContext()).size());
        if (alarmCreateAlarmItemView != null) {
            assertThat(alarmCreateAlarmItemView.getTag(), is(String.class));
            String tag = (String)alarmCreateAlarmItemView.getTag();
            assertThat(tag, is("alarm_create_item"));
        }
    }
}
