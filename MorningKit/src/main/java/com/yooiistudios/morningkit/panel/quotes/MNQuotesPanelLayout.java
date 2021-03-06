package com.yooiistudios.morningkit.panel.quotes;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yooiistudios.morningkit.R;
import com.yooiistudios.morningkit.common.textview.AutoResizeTextView;
import com.yooiistudios.morningkit.common.tutorial.MNTutorialManager;
import com.yooiistudios.morningkit.panel.core.MNPanelLayout;
import com.yooiistudios.morningkit.panel.quotes.model.MNQuote;
import com.yooiistudios.morningkit.panel.quotes.model.MNQuotesLanguage;
import com.yooiistudios.morningkit.panel.quotes.model.MNQuotesLoader;
import com.yooiistudios.morningkit.setting.theme.themedetail.MNTheme;
import com.yooiistudios.morningkit.setting.theme.themedetail.MNThemeType;
import com.yooiistudios.morningkit.theme.MNMainColors;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;

/**
 * Created by StevenKim in MorningKit from Yooii Studios Co., LTD. on 2014. 3. 9.
 *
 * MNQuotesPanelLayout
 */
public class MNQuotesPanelLayout extends MNPanelLayout {
    public static final String QUOTES_STRING = "QUOTES_STRING";
    public static final String QUOTES_LANGUAGES = "QUOTES_LANGUAGES";

    private AutoResizeTextView quoteTextView;

    private List<Boolean> selectedLanguages;
    private MNQuote quote;

    private static final int QUOTES_HANDLER_DELAY = 7000;
    private boolean isHandlerRunning = false;
    private MNQuotesHandler quotesHandler = new MNQuotesHandler();
    private class MNQuotesHandler extends Handler {
        @Override
        public void handleMessage( Message msg ){
            if (MNTutorialManager.isTutorialShown(getContext().getApplicationContext())) {
                // 숨기기
                Animation hideAnimation = AnimationUtils.loadAnimation(
                        getContext().getApplicationContext(), R.anim.quotes_hide);
                if (hideAnimation != null && quoteTextView != null) {
                    hideAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {}
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            // 명언 갱신
                            try {
                                refreshPanel();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // 다시 보여주기
                            Animation showAnimation = AnimationUtils.loadAnimation(
                                    getContext().getApplicationContext(), R.anim.quotes_show);

                            if (showAnimation != null) {
                                quoteTextView.startAnimation(showAnimation);
                            }
                        }
                        @Override
                        public void onAnimationRepeat(Animation animation) {}
                    });
                    quoteTextView.startAnimation(hideAnimation);
                }
            }

            // tick 의 동작 시간을 계산해서 정확히 1초마다 UI 갱신을 요청할 수 있게 구현
            quotesHandler.sendEmptyMessageDelayed(0, QUOTES_HANDLER_DELAY);
        }
    }

    public MNQuotesPanelLayout(Context context) {
        super(context);
    }

    public MNQuotesPanelLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        super.init();

        quoteTextView = new AutoResizeTextView(getContext());
        LayoutParams quoteTextViewLayoutParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        int margin = getResources().getDimensionPixelSize(R.dimen.panel_quotes_padding);
        quoteTextViewLayoutParams.setMargins(margin, margin, margin, margin);
        quoteTextView.setLayoutParams(quoteTextViewLayoutParams);
        quoteTextView.setGravity(Gravity.CENTER);
        quoteTextView.setOnResizeListener(new AutoResizeTextView.OnTextResizeListener() {
            @Override public void onTextResize(TextView textView, float oldSize,
                                     float newSize) {}

            @Override
            public void onEllipsisAdded(TextView textView) {
                MNThemeType currentThemeType = MNTheme.getCurrentThemeType(
                        getContext().getApplicationContext());
                int textColor = MNMainColors.getQuoteContentTextColor
                        (currentThemeType,
                        getContext().getApplicationContext());

                quoteTextView.setTextColor(textColor);
            }
        });

        getContentLayout().addView(quoteTextView);

        if (DEBUG_UI) {
            quoteTextView.setBackgroundColor(Color.parseColor("#cfffcf"));
        }
    }

    @Override
    protected void processLoading() throws JSONException {
        super.processLoading();

        if (getPanelDataObject().has(QUOTES_LANGUAGES)) {
            try {
                String selectedLanguagesJsonString = getPanelDataObject().getString(QUOTES_LANGUAGES);
                Type type = new TypeToken<List<Boolean>>(){}.getType();
                selectedLanguages = new Gson().fromJson(selectedLanguagesJsonString, type);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            initFirstLanguages();
        }
        getRandomQuote();
    }

    private void initFirstLanguages() throws JSONException {
        // 현재 언어에 따라 첫 명언 언어 설정해주기
        selectedLanguages = MNQuotesLanguage.initFirstQuoteLanguage(getContext());

        // 초기화 이후 panelDataObject 에 저장
        getPanelDataObject().put(QUOTES_LANGUAGES, new Gson().toJson(selectedLanguages));
    }

    private void getRandomQuote() {
        // 해당 언어에 따라 명언 골라주기
        // while 이 이상적이지만 혹시나 모를 무한루프 방지를 위해 100번만 돌림
        Random randomGenerator = new Random();
        int randomLanguageIndex = 0;
        for (int i = 0; i < 100; i++) {
            randomLanguageIndex = randomGenerator.nextInt(selectedLanguages.size());
            if (selectedLanguages.get(randomLanguageIndex)) {
                break;
            }
        }

        // 랜덤 명언 얻기
        MNQuotesLanguage quotesLanguage = MNQuotesLanguage.values()[randomLanguageIndex];
        quote = MNQuotesLoader.getRandomQuote(getContext(), quotesLanguage);
    }

    @Override
    protected void updateUI() {
        super.updateUI();

        if (quote != null) {
            quoteTextView.setVisibility(View.VISIBLE);
            applyTheme(); // 여기서 조립과 색 적용을 모두 함
        } else {
            quoteTextView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void archivePanelData() throws JSONException {
        super.archivePanelData();

        // 리프레시 된 명언을 추가해주기
        String quoteJsonString = new Gson().toJson(quote);
        getPanelDataObject().put(QUOTES_STRING, quoteJsonString);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void applyTheme() {
        super.applyTheme();

        if (quote != null) {
            MNThemeType currentThemeType = MNTheme.getCurrentThemeType(
                    getContext().getApplicationContext());

            SpannableStringBuilder stringBuilder = new SpannableStringBuilder();

            // 명언 텍스트 조립
            SpannableString contentString = new SpannableString(quote.getQuote());
                // 폰트 색
            contentString.setSpan(
                    new ForegroundColorSpan(MNMainColors.getQuoteContentTextColor(currentThemeType, getContext().getApplicationContext())),
                    0, contentString.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            stringBuilder.append(contentString);

            // 내용과 저자 텍스트 사이 간격 주기(텍스트 사이즈를 줄여서 한 줄의 높이를 적당히 조절)
            SpannableString emptyString = new SpannableString("\n\n");
            emptyString.setSpan(new RelativeSizeSpan(0.4f), 0, emptyString.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            stringBuilder.append(emptyString);

            // 저자 텍스트 조립
            SpannableString authorString = new SpannableString(quote.getAuthor());
                // 폰트 색
            authorString.setSpan(
                    new ForegroundColorSpan(MNMainColors.getQuoteAuthorTextColor(currentThemeType, getContext().getApplicationContext())),
                    0, authorString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                // 크기 좀 더 작게 표시
            authorString.setSpan(new RelativeSizeSpan(0.85f), 0, authorString.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            stringBuilder.append(authorString);

            // 방향에 따라 최초 사이즈를 약간 다르게 주기
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                quoteTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimensionPixelSize(R.dimen.panel_quotes_default_font_size_port));
            } else {
                quoteTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimensionPixelSize(R.dimen.panel_quotes_default_font_size_land));
            }
            quoteTextView.setText(stringBuilder, TextView.BufferType.SPANNABLE);
        }
    }

    private void startHandler() {
        if (isHandlerRunning) {
            return;
        }
        isHandlerRunning = true;
        quotesHandler.sendEmptyMessageDelayed(0, QUOTES_HANDLER_DELAY);
    }

    private void stopHandler() {
        if (!isHandlerRunning) {
            return;
        }
        isHandlerRunning = false;
        quotesHandler.removeMessages(0);
    }

    // 뷰가 붙을 때 아날로그 시계뷰 재가동
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startHandler();
    }

    // 뷰가 사라질 때 아날로그 시계뷰 핸들러 중지
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopHandler();
    }
}
