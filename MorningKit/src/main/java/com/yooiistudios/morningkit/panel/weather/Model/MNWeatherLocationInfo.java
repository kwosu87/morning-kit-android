package com.yooiistudios.morningkit.panel.weather.Model;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by StevenKim in MorningKit from Yooii Studios Co., LTD. on 2014. 3. 21.
 *
 * MNWeatherLocationInfo
 *  도시 검색 & 현재 위치 사용시 얻는 도시 정보
 */
public class MNWeatherLocationInfo {
    String name;
    String regionCode;
    String countryCode;
    String timezoneCode;
    float latitude;
    float longitude;

    // 추가: woeid
    // originalName(현재에서 사용되는 언어의 도시이름 ex:서울), otherNames 추가: originalName이 검색되면 이것을 보여 주고,
    // toherNames가 검색되면 englishName(name)을 보여 줌

    int woeid;
    String englishName;
    String originalName;
    ArrayList<String> otherNames;

    // 추가 time stamp
    Calendar timestamp;

    // 현지 시간을 위한 time offset (초)
    int timeOffset;
}
