package com.yooiistudios.morningkit.panel.newsfeed.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.yooiistudios.morningkit.panel.newsfeed.model.MNNewsFeedUrl;

import java.lang.reflect.Type;
import java.util.ArrayList;

import nl.matshofman.saxrssreader.RssFeed;
import nl.matshofman.saxrssreader.RssItem;

import static com.yooiistudios.morningkit.panel.newsfeed.MNNewsFeedPanelLayout.KEY_FEED_URL;
import static com.yooiistudios.morningkit.panel.newsfeed.MNNewsFeedPanelLayout.PREF_NEWS_FEED;

/**
 * Created by Dongheyon Jeong on in morning-kit from Yooii Studios Co., LTD. on 2014. 7. 3.
 *
 * MNNewsFeedUtil
 *  뉴스피드 모델을 처리하는 로직을 가진 클래스
 */
public class MNNewsFeedUtil {
    private static final String KEY_HISTORY = "url hisotry";
    private static final int MAX_HISTORY_SIZE = 10;

    public static String getRssFeedJsonString(RssFeed feed) {
        return new GsonBuilder().setExclusionStrategies(new ExclusionStrategy
                () {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return false;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return (clazz == RssItem.class);
                    }
                }).serializeNulls().create().toJson(feed);
    }

    public static String getRssItemArrayListString(ArrayList<RssItem>
                                                           itemList) {
        return new GsonBuilder().setExclusionStrategies(new ExclusionStrategy
                () {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return false;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return (clazz == RssFeed.class);
            }
        }).serializeNulls().create().toJson(itemList);
    }

    public static void saveNewsFeedUrl(Context context, MNNewsFeedUrl newsFeedUrl) {
        SharedPreferences prefs = context.getApplicationContext()
                .getSharedPreferences(PREF_NEWS_FEED, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_FEED_URL, new Gson().toJson(newsFeedUrl)).apply();
    }

    public static void removeNewsFeedUrl(Context context) {
        SharedPreferences prefs = context.getApplicationContext()
                .getSharedPreferences(PREF_NEWS_FEED, Context.MODE_PRIVATE);
        prefs.edit().remove(KEY_FEED_URL).apply();
    }

    public static void addUrlToHistory(Context context, String url) {
        ArrayList<String> urlList = getUrlHistory(context);

        // if list contains url, remove and add it at 0th index.
        if (urlList.contains(url)) {
            urlList.remove(url);
        }
        // put recent url at 0th index.
        urlList.add(0, url);

        // remove last history if no room.
        if (urlList.size() > MAX_HISTORY_SIZE) {
            urlList.remove(urlList.size()-1);
        }

        SharedPreferences prefs = context.getSharedPreferences(
                PREF_NEWS_FEED, Context.MODE_PRIVATE);

        prefs.edit().putString(KEY_HISTORY, new Gson().toJson(urlList)).apply();
    }

    public static ArrayList<String> getUrlHistory(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                PREF_NEWS_FEED, Context.MODE_PRIVATE);
        String historyJsonStr = prefs.getString(KEY_HISTORY, null);

        if (historyJsonStr != null) {
            Type type = new TypeToken<ArrayList<String>>(){}.getType();
            return new Gson().fromJson(historyJsonStr, type);
        }
        else {
            return new ArrayList<String>();
        }
    }

    public static String makeLanguageRegionCode(String languageCode, String regionCode) {
        String key = languageCode;
        if (regionCode != null && regionCode.length() > 0) {
            key += "_" + regionCode;
        }
        if (key != null) {
            return key.toLowerCase();
        } else {
            return "";
        }
    }
}
