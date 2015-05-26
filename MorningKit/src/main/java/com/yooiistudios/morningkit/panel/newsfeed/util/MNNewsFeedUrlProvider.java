package com.yooiistudios.morningkit.panel.newsfeed.util;

import android.content.Context;

import com.yooiistudios.morningkit.common.locale.MNLocaleUtils;
import com.yooiistudios.morningkit.panel.newsfeed.model.MNNewsFeedUrl;
import com.yooiistudios.morningkit.panel.newsfeed.model.MNNewsFeedUrlType;
import com.yooiistudios.morningkit.panel.newsfeed.model.MNNewsProviderCountry;
import com.yooiistudios.morningkit.panel.newsfeed.model.MNNewsProviderLangType;
import com.yooiistudios.morningkit.panel.newsfeed.model.MNNewsProviderLanguage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Locale;

/**
 * Created by Dongheyon Jeong in morning-kit from Yooii Studios Co., LTD. on 15. 5. 25.
 *
 * MNDefaultNewsFeedUrlProvider
 *  Curation List 를 파싱, 제공하는 클래스
 */
public class MNNewsFeedUrlProvider {
//    private static final LinkedHashMap<Locale, MNNewsFeedUrl> DEFAULT_LOCALE_URL_MAP;
//    private static final MNLocale REUSABLE_LOCALE = new MNLocale("en", "US");

//    static {
//        DEFAULT_LOCALE_URL_MAP = new LinkedHashMap<Locale, MNNewsFeedUrl>();
//        putDefaultLocaleUrls("en", "US",
//                "http://news.google.com/news?cf=all&ned=us&hl=en&output=rss",
//                MNNewsFeedUrlType.GOOGLE);
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("en", "US"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=us&hl=en&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("en", "CA"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=ca&hl=en&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("en", "GB"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=uk&hl=en&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("en", "IE"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=en_ie&hl=en&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("en", "AU"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=au&hl=en&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("en", "NZ"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=nz&hl=en&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("en", "KR"),
//                new MNNewsFeedUrl(
//                        "http://biz.heraldcorp.com/common/rss_xml.php?ct=101",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("en", "JP"),
//                new MNNewsFeedUrl(
//                        "http://feeds.feedburner.com/japantimes",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("en", "CN"),
//                new MNNewsFeedUrl(
//                        "http://www.chinadaily.com.cn/rss/china_rss.xml",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("en", "SG"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=en_sg&hl=en&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("en", "IN"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=in&hl=en&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("en", "PH"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=en_ph&hl=en&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("en", "MY"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=en_my&hl=en&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("en", "PK"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=en_pk&hl=en&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("en", "ZA"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=en_za&hl=en&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("en", "NG"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=en_ng&hl=en&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("en", "ET"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=en_et&hl=en&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("en", "TZ"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=en_tz&hl=en&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("en", "KE"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=en_ke&hl=en&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("fr", "FR"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=fr&hl=fr&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("fr", "CA"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=fr_ca&hl=fr&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("fr", "BE"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?pz=1&cf=all&ned=fr_be&hl=fr&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("fr", "CH"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=fr_ch&hl=fr&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("fr", "LU"),
//                new MNNewsFeedUrl(
//                        "http://www.lequotidien.lu/feed/",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("de", "DE"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=de&hl=de&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("de", "AT"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=de_at&hl=de&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("de", "CH"),
//                new MNNewsFeedUrl(
//                        "http://www.20min.ch/rss/rss.tmpl?type=channel&get=1",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("de", "LU"),
//                new MNNewsFeedUrl(
//                        "http://www.tageblatt.lu/rss/front.tmpl",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("ru", "RU"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=ru_ru&hl=ru&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("ru", "UA"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=ru_ua&hl=ru&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("ru", "KZ"),
//                new MNNewsFeedUrl(
//                        "http://www.caravan.kz/rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("ru", "BY"),
//                new MNNewsFeedUrl(
//                        "http://www.nv-online.info/feed/index.rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("sv", "SE"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=sv_se&hl=sv&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("es", "ES"),
//                new MNNewsFeedUrl(
//                        "http://ep00.epimg.net/rss/tags/ultimas_noticias.xml",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("es", "MX"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=es_mx&hl=es&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("es", "CO"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=es_co&hl=es&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("es", "AR"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=es_ar&hl=es&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("es", "PE"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=es_pe&hl=es&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("es", "VE"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=es_ve&hl=es&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("es", "CL"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=es_cl&hl=es&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("es", "EC"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=es_us&hl=es&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("es", "BO"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=es_us&hl=es&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("es", "GT"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=es_us&hl=es&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("es", "PY"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=es_us&hl=es&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("es", "UY"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=es_us&hl=es&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("es", "CU"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=es_cu&hl=es&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("es", "DO"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=es_us&hl=es&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("es", "HN"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=es_us&hl=es&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("es", "NI"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=es_us&hl=es&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("es", "SV"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=es_us&hl=es&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("es", "CR"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=es_us&hl=es&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("es", "PA"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=es_us&hl=es&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("es", "PR"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=es_us&hl=es&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("es", "JM"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=es_us&hl=es&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("pt", "PT"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=pt-PT_pt&hl=pt-PT&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("pt", "BR"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=pt-BR_br&hl=pt-BR&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("it", "IT"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=it&hl=it&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("nl", "NL"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=nl_nl&hl=nl&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("nl", "BE"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=nl_be&hl=nl&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("nb", "NO"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=no_no&hl=no&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("fi", "FI"),
//                new MNNewsFeedUrl(
//                        "http://www.iltasanomat.fi/rss/tuoreimmat.xml",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("da", "DK"),
//                new MNNewsFeedUrl(
//                        "http://jp.dk/?service=rssfeed&submode=topnyheder",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("tr", "TR"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=tr_tr&hl=tr&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("uk", "UA"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=uk_ua&hl=uk&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("pl", "PL"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=pl_pl&hl=pl&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("ro", "RO"),
//                new MNNewsFeedUrl(
//                        "http://jurnalul.ro/rss/stiri.xml",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("kk", "KZ"),
//                new MNNewsFeedUrl(
//                        "http://www.time.kz/rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("el", "GR"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=el_gr&hl=el&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("cs", "CZ"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=cs_cz&hl=cs&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("hu", "HU"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=hu_hu&hl=hu&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("be", "BY"),
//                new MNNewsFeedUrl(
//                        "http://nn.by/?c=rss-top",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("bg", "BG"),
//                new MNNewsFeedUrl(
//                        "http://www.dnevnik.bg/rss/",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("sr", "RS"),
//                new MNNewsFeedUrl(
//                        "http://www.danas.rs/rss/rss.asp?column_id=27",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("sk", "SK"),
//                new MNNewsFeedUrl(
//                        "http://spravy.pravda.sk/rss/xml/s-obrazkom/",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("hr", "HR"),
//                new MNNewsFeedUrl(
//                        "http://www.24sata.hr/feeds/aktualno.xml",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("ee", "EE"),
//                new MNNewsFeedUrl(
//                        "http://www.aripaev.ee/mod/rss.xml",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("lt", "LT"),
//                new MNNewsFeedUrl(
//                        "http://www.lrytas.lt/rss/",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("lv", "LV"),
//                new MNNewsFeedUrl(
//                        "http://www.diena.lv/top_news.rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("zh", "CN"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=cn&hl=zh-CN&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("zh", "TW"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=tw&hl=zh-TW&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("zh", "HK"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=hk&hl=zh-TW&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("ko", "KR"),
//                new MNNewsFeedUrl(
//                        "http://news.google.co.kr/news?pz=1&cf=all&ned=kr&hl=ko&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("ja", "JP"),
//                new MNNewsFeedUrl(
//                        "http://www3.asahi.com/rss/index.rdf",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("vi", "VN"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=vi_vn&hl=vi&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("th", "TH"),
//                new MNNewsFeedUrl(
//                        "http://www.khaosod.co.th/rss/urgent_news.xml",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("ms", "MY"),
//                new MNNewsFeedUrl(
//                        "http://www.bharian.com.my/terkini.xml",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("in", "ID"),
//                new MNNewsFeedUrl(
//                        "http://www.suaramerdeka.com/rsssm/index.php/news",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("ar", "EG"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=ar_me&hl=ar&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("ar", "IQ"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=ar_me&hl=ar&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("ar", "SA"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=ar_me&hl=ar&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("ar", "YE"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=ar_me&hl=ar&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("ar", "SY"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=ar_me&hl=ar&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("ar", "LB"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=ar_me&hl=ar&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("fa", "IR"),
//                new MNNewsFeedUrl(
//                        "http://www.donya-e-eqtesad.com/general-news/rss/",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("hi", "IN"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=hi_in&hl=hi&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale("il", "IL"),
//                new MNNewsFeedUrl(
//                        "http://news.google.com/news?cf=all&ned=iw_il&hl=iw&output=rss",
//                        MNNewsFeedUrlType.GOOGLE
//                )
//        );
//    }

//    private static void putDefaultLocaleUrls(String langCode, String countryCode,
//                                             String url, MNNewsFeedUrlType urlType) {
//        DEFAULT_LOCALE_URL_MAP.put(
//                new Locale(langCode, countryCode),
//                new MNNewsFeedUrl(url, urlType)
//        );
//    }

    /**
     * Singleton
     */
    private volatile static MNNewsFeedUrlProvider instance;

//    private ArrayList<MNNewsProviderLanguage> mNewsLanguages;
    private LinkedHashMap<String, MNNewsProviderLanguage> mNewsLanguages;
    private Context mContext;

    public static MNNewsFeedUrlProvider getInstance(Context context) {
        if (instance == null) {
            synchronized (MNNewsFeedUrlProvider.class) {
                if (instance == null) {
                    instance = new MNNewsFeedUrlProvider(context);
                }
            }
        }
        return instance;
    }

    private MNNewsFeedUrlProvider(Context context) {
        mContext = context.getApplicationContext();
        mNewsLanguages = new LinkedHashMap<String, MNNewsProviderLanguage>();
        for (int i = 0; i < MNNewsProviderLangType.values().length; i++) {
            MNNewsProviderLangType type = MNNewsProviderLangType.valueOf(i);
            MNNewsProviderLanguage newsProviderLanguage
                    = parseNewsProvidersByResource(context, type.getResourceId());
            String languageRegionCode = makeLanguageRegionCode(
                    newsProviderLanguage.languageCode,
                    newsProviderLanguage.regionCode
            );
            mNewsLanguages.put(languageRegionCode, newsProviderLanguage);
        }
    }

    public MNNewsFeedUrl getDefault() {
        Locale defaultLocale = MNLocaleUtils.loadDefaultLocale(mContext);
        String langCode = defaultLocale.getLanguage();
        String countryCode = defaultLocale.getCountry();

        MNNewsProviderLanguage newsProviderLanguage = mNewsLanguages.get(langCode);
        MNNewsProviderCountry providerCountry = newsProviderLanguage.newsProviderCountries.get(countryCode);

        // TODO: MNNewsFeedUrlType 지정
        return new MNNewsFeedUrl(providerCountry.url, MNNewsFeedUrlType.GOOGLE,
                providerCountry.languageCode, providerCountry.regionCode, providerCountry.countryCode);
    }

    public LinkedHashMap<String, MNNewsProviderLanguage> getUrlsSortedByLocale() {
        Locale locale = MNLocaleUtils.loadDefaultLocale(mContext);
        LinkedHashMap<String, MNNewsProviderLanguage> clonedLanguageProviders
                = new LinkedHashMap<String, MNNewsProviderLanguage>(mNewsLanguages);
        LinkedHashMap<String, MNNewsProviderLanguage> sortedLanguageProviders
                = new LinkedHashMap<String, MNNewsProviderLanguage>();

        String languageCode = locale.getLanguage();
        String countryCode = locale.getCountry();
        if (languageCode.equals("fr")) {
            // French English German Dutch Spanish Portuguese [French Spanish Portuguese 공통] 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "fr");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "de");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "nl");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "es");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "pt");

            putFrenchSpanishPortugueseNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders);
        } else if (languageCode.equals("es")) {
            // Spanish English Portuguese French German	Dutch [French Spanish Portuguese 공통] 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "es");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "pt");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "fr");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "de");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "nl");

            putFrenchSpanishPortugueseNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders);
        } else if (languageCode.equals("pt")) {
            // Portuguese English Spanish French German	Dutch [French Spanish Portuguese 공통] 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "pt");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "es");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "fr");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "de");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "nl");

            putFrenchSpanishPortugueseNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders);
        } else if (languageCode.equals("hi")) {
            // Hindi 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "hi");
        } else if (languageCode.equals("zh") && countryCode.equals("CN")) {
            // S-Chinese T-Chinese English Japanese	Korean 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "zh", "cn");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "zh", "tw");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ja");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ko");
        } else if (languageCode.equals("zh") && countryCode.equals("TW")) {
            // T-Chinese S-Chinese English Japanese	Korean 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "zh", "tw");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "zh", "cn");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ja");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ko");
        } else if (languageCode.equals("ja")) {
            // Japanese English S-Chinese T-Chinese Korean 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ja");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "zh", "cn");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "zh", "tw");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ko");
        } else if (languageCode.equals("ko")) {
            // Korean English Japanese S-Chinese T-Chinese 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ko");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ja");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "zh", "cn");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "zh", "tw");
        } else if (languageCode.equals("vi")) {
            // Vietnamese English S-Chinese T-Chinese Japanese Korean Thai 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "vi");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "zh", "cn");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "zh", "tw");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ja");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ko");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "th");
        } else if (languageCode.equals("ar")) {
            // Arabic Persian 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ar");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "fa");
        } else if (languageCode.equals("fa")) {
            // Persian Arabic 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "fa");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ar");
        } else if (languageCode.equals("ru")) {
            // Russian English Kazakh Ukrainian Estonian Lithuanian Latvian 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ru");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "kk");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "uk");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ee");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "lt");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "lv");
        } else if (languageCode.equals("de")) {
            // German 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "de");
        } else if (languageCode.equals("nl")) {
            // Dutch English German 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "nl");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "de");
        } else if (languageCode.equals("it")) {
            // Italian English French Spanish Portuguese German 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "it");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "fr");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "es");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "pt");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "de");
        } else if (languageCode.equals("nb")) {
            // Norwegian English Swedish Finnish Danish 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "nb");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "sv");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "fi");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "da");
        } else if (languageCode.equals("sv")) {
            // Swedish English Norwegian Finnish Danish 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "sv");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "nb");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "fi");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "da");
        } else if (languageCode.equals("fi")) {
            // Finnish English Swedish Norwegian Danish 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "fi");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "sv");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "nb");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "da");
        } else if (languageCode.equals("da")) {
            // Danish English Norwegian Swedish Finnish 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "da");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "nb");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "sv");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "fi");
        } else if (languageCode.equals("tr")) {
            // Turkish English Italian Spanish Portuguese French 우선정렬
            // Turkish English Greek 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "tr");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "el");
        } else if (languageCode.equals("el")) {
            // Greek English Turkish 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "el");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "tr");
        } else if (languageCode.equals("cs")) {
            // Czech English Slovak 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "cs");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "sk");
        } else if (languageCode.equals("sk")) {
            // Slovak English Czech 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "sk");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "cs");
        } else if (languageCode.equals("ee")) {
            // Estonian English Lithuanian Latvian 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ee");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "lt");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "lv");
        } else if (languageCode.equals("lt")) {
            // Lithuanian English Estonian Latvian 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "lt");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ee");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "lv");
        } else if (languageCode.equals("lv")) {
            // Latvian English Estonian Lithuanian 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "lv");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ee");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "lt");
        } else if (languageCode.equals("pl")) {
            // Polish English Russian 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "pl");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ru");
        } else if (languageCode.equals("ro")) {
            // Romanian English Russian 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ro");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ru");
        } else if (languageCode.equals("hr")) {
            // Croatian English Russian 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "hr");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ru");
        } else if (languageCode.equals("kk")) {
            // Kazakh English Russian 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "kk");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ru");
        } else if (languageCode.equals("uk")) {
            // Ukrainian English Russian 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "uk");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ru");
        } else if (languageCode.equals("hu")) {
            // Hungarian English Russian 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "hu");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ru");
        } else if (languageCode.equals("bg")) {
            // Bulgarian English Russian 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "bg");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ru");
        } else if (languageCode.equals("be")) {
            // Belarusian English Russian 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "be");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ru");
        } else if (languageCode.equals("sr")) {
            // Serbian English Russian 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "sr");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ru");
        } else if (languageCode.equals("il")) {
            // Hebrew English Arabic Persian 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "il");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ar");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "fa");
        } else if (languageCode.equals("th")) {
            // Thai English Vietnamese Malay 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "th");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "vi");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ms");
        } else if (languageCode.equals("ms")) {
            // Malay English Thai Vietnamese 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ms");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "th");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "vi");
        } else if (languageCode.equals("in")) {
            // Indonesia English Malay 우선정렬
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "in");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "en");
            putNewsProviderLanguage(clonedLanguageProviders, sortedLanguageProviders, "ms");
        }

        if (sortedLanguageProviders.size() == 0) {
            sortedLanguageProviders = clonedLanguageProviders;
        } else {
            LinkedHashSet<String> keySet = new LinkedHashSet<String>(clonedLanguageProviders.keySet());

            for (String clonedKey : keySet) {
                sortedLanguageProviders.put(clonedKey, clonedLanguageProviders.remove(clonedKey));
            }
        }

        return sortedLanguageProviders;
    }

    private static void putFrenchSpanishPortugueseNewsProviderLanguage(
            LinkedHashMap<String, MNNewsProviderLanguage> clonedNewsProviders,
            LinkedHashMap<String, MNNewsProviderLanguage> sortedNewsProviders) {
        // Italian Norwegian Swedish Finnish Danish	Turkish	Greek Russian Czech Slovak Estonian
        // Lithuanian Latvian Polish Romanian Croatian Kazakh Ukrainian Hungarian Bulgarian
        // Belarusian Serbian Hebrew
        putNewsProviderLanguage(clonedNewsProviders, sortedNewsProviders, "it");
        putNewsProviderLanguage(clonedNewsProviders, sortedNewsProviders, "nb");
        putNewsProviderLanguage(clonedNewsProviders, sortedNewsProviders, "sv");
        putNewsProviderLanguage(clonedNewsProviders, sortedNewsProviders, "fi");
        putNewsProviderLanguage(clonedNewsProviders, sortedNewsProviders, "da");
        putNewsProviderLanguage(clonedNewsProviders, sortedNewsProviders, "tr");
        putNewsProviderLanguage(clonedNewsProviders, sortedNewsProviders, "el");
        putNewsProviderLanguage(clonedNewsProviders, sortedNewsProviders, "ru");
        putNewsProviderLanguage(clonedNewsProviders, sortedNewsProviders, "cs");
        putNewsProviderLanguage(clonedNewsProviders, sortedNewsProviders, "sk");
        putNewsProviderLanguage(clonedNewsProviders, sortedNewsProviders, "ee");
        putNewsProviderLanguage(clonedNewsProviders, sortedNewsProviders, "lt");
        putNewsProviderLanguage(clonedNewsProviders, sortedNewsProviders, "lv");
        putNewsProviderLanguage(clonedNewsProviders, sortedNewsProviders, "pl");
        putNewsProviderLanguage(clonedNewsProviders, sortedNewsProviders, "ro");
        putNewsProviderLanguage(clonedNewsProviders, sortedNewsProviders, "hr");
        putNewsProviderLanguage(clonedNewsProviders, sortedNewsProviders, "kk");
        putNewsProviderLanguage(clonedNewsProviders, sortedNewsProviders, "uk");
        putNewsProviderLanguage(clonedNewsProviders, sortedNewsProviders, "hu");
        putNewsProviderLanguage(clonedNewsProviders, sortedNewsProviders, "bg");
        putNewsProviderLanguage(clonedNewsProviders, sortedNewsProviders, "be");
        putNewsProviderLanguage(clonedNewsProviders, sortedNewsProviders, "sr");
        putNewsProviderLanguage(clonedNewsProviders, sortedNewsProviders, "il");
    }

    private static void putNewsProviderLanguage(LinkedHashMap<String, MNNewsProviderLanguage> cloned,
                                                LinkedHashMap<String, MNNewsProviderLanguage> sorted,
                                                String languageCode) {
        putNewsProviderLanguage(cloned, sorted, languageCode, "");
    }

    private static void putNewsProviderLanguage(LinkedHashMap<String, MNNewsProviderLanguage> cloned,
                                                LinkedHashMap<String, MNNewsProviderLanguage> sorted,
                                                String languageCode, String regionCode) {
        String languageRegionCode = makeLanguageRegionCode(languageCode, regionCode);
        sorted.put(languageRegionCode, cloned.remove(languageRegionCode));
    }

    private static MNNewsProviderLanguage parseNewsProvidersByResource(Context context, int resourceId) {
        // raw id 에서 json 스트링을 만들고 JSONObject 로 변환
        try {
            InputStream file;
            file = context.getResources().openRawResource(resourceId);

            BufferedReader reader = new BufferedReader(new InputStreamReader(file, "UTF-8"));
            char[] buffer = new char[file.available()];
            reader.read(buffer);

            // 변환한 JSONObject 를 NewsProviders 로 변환
            JSONObject newsData = new JSONObject(new String(buffer));

            // NewsProviderLanguages
            MNNewsProviderLanguage newsProviderLanguage = new MNNewsProviderLanguage();
            newsProviderLanguage.englishLanguageName = newsData.getString("lang_name_english");
            newsProviderLanguage.regionalLanguageName = newsData.getString("lang_name_regional");
            newsProviderLanguage.languageCode = newsData.getString("lang_code");
            String regionCode = newsData.getString("region_code");
            if (!regionCode.equals("")) {
                newsProviderLanguage.regionCode = regionCode;
            }

            // NewsProviderCountries
            JSONArray newsProviderCountryArray = newsData.getJSONArray("countries");
            newsProviderLanguage.newsProviderCountries = parseNewsProviderCountries(
                    newsProviderCountryArray, newsProviderLanguage);

            return newsProviderLanguage;
        } catch (IOException ignored) {
        } catch (JSONException ignored) {
        }
        return null;
    }

    private static LinkedHashMap<String, MNNewsProviderCountry> parseNewsProviderCountries(
            JSONArray newsProviderCountryArray, MNNewsProviderLanguage newsProviderLanguage) throws JSONException {

        LinkedHashMap<String, MNNewsProviderCountry> newsProviderCountries = new LinkedHashMap<String, MNNewsProviderCountry>();

        for (int i = 0; i < newsProviderCountryArray.length(); i++) {
            JSONObject newsProviderCountryObject = newsProviderCountryArray.getJSONObject(i);
            MNNewsProviderCountry newsProviderCountry = new MNNewsProviderCountry();
            newsProviderCountry.languageCode = newsProviderLanguage.languageCode;
            newsProviderCountry.regionCode = newsProviderLanguage.regionCode;
            newsProviderCountry.countryLocalName = newsProviderCountryObject.getString("country_name");
            newsProviderCountry.countryCode = newsProviderCountryObject.getString("country_code");
            newsProviderCountry.url = newsProviderCountryObject.getString("url");

            newsProviderCountries.put(newsProviderCountry.countryCode, newsProviderCountry);
        }
        return newsProviderCountries;
    }

    private static String makeLanguageRegionCode(String languageCode, String regionCode) {
        String key = languageCode;
        if (regionCode != null && regionCode.length() > 0) {
            key += "_" + regionCode;
        }
        return key;
    }

//    private static class MNLocale {
//        private String mLanguageCode = "";
//        private String mCountryCode = "";
//
//        public MNLocale(String languageCode) {
//            checkNull(languageCode);
//            this.mLanguageCode = languageCode;
//        }
//
//        public MNLocale(String languageCode, String countryCode) {
//            this(languageCode);
//
//            this.mCountryCode = countryCode != null ? countryCode : "";
//        }
//
//        public String getLanguageCode() {
//            return mLanguageCode;
//        }
//
//        public void setLanguageCode(String languageCode) {
//            checkNull(languageCode);
//            mLanguageCode = languageCode;
//        }
//
//        public String getCountryCode() {
//            return mCountryCode;
//        }
//
//        public void setCountryCode(String countryCode) {
//            this.mCountryCode = countryCode != null ? countryCode : "";
//        }
//
//        public void clear() {
//            mLanguageCode = "";
//            mCountryCode = "";
//        }
//
//        private static void checkNull(String code) {
//            if (code == null) {
//                throw new IllegalArgumentException("code MUST NOT BE NULL!!");
//            }
//        }
//
//        @Override
//        public boolean equals(Object object) {
//            if (object == this) {
//                return true;
//            }
//            if (object instanceof MNLocale) {
//                MNLocale o = (MNLocale) object;
//                return mLanguageCode.equals(o.mLanguageCode)
//                        && mCountryCode.equals(o.mCountryCode);
//            }
//            return false;
//        }
//
//        @Override
//        public synchronized int hashCode() {
//            return mCountryCode.hashCode() + mLanguageCode.hashCode();
//        }
//    }
}
