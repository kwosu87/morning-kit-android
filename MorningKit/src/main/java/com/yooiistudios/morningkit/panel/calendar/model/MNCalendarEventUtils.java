package com.yooiistudios.morningkit.panel.calendar.model;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by StevenKim in MorningKit from Yooii Studios Co., LTD. on 2014. 4. 15.
 *
 * MNCalendarEventUtils
 *  캘린더 ID를 통해 Event 를 가져오는 유틸리티 클래스.
 *  캘린더 갯수가 많아질 경우 퍼포먼스가 떨어지기에 AsyncTask 에서 활용하기 위해 리팩토링을 시도함
 */
public class MNCalendarEventUtils {
    private MNCalendarEventUtils() { throw new AssertionError("You MUST not create this class!"); }
    public static MNCalendarEventList getCalendarEventList(Context context, boolean[] selectedArr) {

        // Calendar Ids
        ArrayList<MNCalendar> calendarModels;
        if (android.os.Build.VERSION.SDK_INT >= 14) {
            calendarModels = MNCalendarFetcher.getCalendarModels14(context);
        } else {
            calendarModels = MNCalendarFetcher.getCalendarModels(context);
        }

        // 여기서 RuntimeException 때문에 죽는 경우가 있어 예외 처리함
        try {
            if (calendarModels != null) {
                // apply calendar selection
                for (int i = 0; i < calendarModels.size(); i++) {
                    MNCalendar calendarModel = calendarModels.get(i);
                    // 저장된 정보와 캘린더 숫자가 변할 가능성을 염두에 두고 방어적 코드 삽입
                    if (selectedArr != null && i < selectedArr.length) {
                        calendarModel.selected = selectedArr[i];
                    }
                }

                // 미리 정렬된 결과를 얻을 수 있음
                if (android.os.Build.VERSION.SDK_INT >= 14) {
                    return MNCalendarFetcher.getCalendarEvents14(context, calendarModels);
                } else {
                    return MNCalendarFetcher.getCalendarEvents(context, calendarModels);
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
