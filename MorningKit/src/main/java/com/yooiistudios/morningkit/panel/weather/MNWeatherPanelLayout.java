package com.yooiistudios.morningkit.panel.weather;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stevenkim.waterlily.bitmapfun.ui.RecyclingImageView;
import com.stevenkim.waterlily.bitmapfun.util.RecyclingBitmapDrawable;
import com.yooiistudios.morningkit.R;
import com.yooiistudios.morningkit.panel.core.MNPanelLayout;
import com.yooiistudios.morningkit.panel.weather.model.MNWeatherLocationInfo;

import org.json.JSONException;

import java.lang.reflect.Type;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by StevenKim in MorningKit from Yooii Studios Co., LTD. on 2014. 3. 19.
 *
 * MNWeatherPanelLayout
 */
public class MNWeatherPanelLayout extends MNPanelLayout {

    protected static final String WEATHER_DATA_IS_USING_CURRENT_LOCATION = "WEATHER_IS_USING_CURRENT_LOCATION";
    protected static final String WEATHER_DATA_IS_DISPLAYING_LOCAL_TIME = "WEATHER_INDICATE_LOCAL_TIME";
    protected static final String WEATHER_DATA_TEMP_CELSIUS = "WEATHER_TEMP_CELSIUS";
    protected static final String WEATHER_DATA_SELECTED_WEATHER_LOCATION_INFO = "WEATHER_DATA_SELECTED_WEATHER_LOCATION_INFO";

    // UI
    private RelativeLayout innerContentLayout;
    private RelativeLayout upperContentLayout;
    private RelativeLayout upperTempContentLayout;
    private RelativeLayout upperTempInnerContentLayout;
    private RecyclingImageView weatherConditionImageView;
    private TextView currentTempTextView;
    private TextView lowHighTempTextView;
    private TextView cityNameTextView;
    private TextView localTimeTextView;

    // Model
    private boolean isUsingCurrentLocation = true;
    private boolean isDisplayingLocaltime = true;
    private boolean isUsingCelsius = true;
    private MNWeatherLocationInfo selectedLocationInfo;

    public MNWeatherPanelLayout(Context context) {
        super(context);
    }
    public MNWeatherPanelLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        super.init();

        // containers
        innerContentLayout = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams innerLayoutParams = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        innerLayoutParams.addRule(CENTER_IN_PARENT);
        innerContentLayout.setLayoutParams(innerLayoutParams);
        getContentLayout().addView(innerContentLayout);

        // upper layout
        upperContentLayout = new RelativeLayout(getContext());
        upperContentLayout.setId(9123857);
        RelativeLayout.LayoutParams upperLayoutParams = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        upperLayoutParams.addRule(CENTER_HORIZONTAL);
        upperContentLayout.setLayoutParams(upperLayoutParams);
        innerContentLayout.addView(upperContentLayout);

        // image
        weatherConditionImageView = new RecyclingImageView(getContext());
        weatherConditionImageView.setId(8213774);
        int imageSize = getResources().getDimensionPixelSize(R.dimen.panel_weather_condition_image_size);
        RelativeLayout.LayoutParams imageViewParams = new LayoutParams(imageSize, imageSize);
        weatherConditionImageView.setLayoutParams(imageViewParams);
        weatherConditionImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        upperContentLayout.addView(weatherConditionImageView);

        int marginOuter = getResources().getDimensionPixelSize(R.dimen.margin_outer);

        // upper temp layout
        upperTempContentLayout = new RelativeLayout(getContext());
        upperTempContentLayout.setId(1323857);
        RelativeLayout.LayoutParams upperTempLayoutParams = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        upperTempLayoutParams.addRule(RIGHT_OF, weatherConditionImageView.getId());
        upperTempLayoutParams.addRule(ALIGN_TOP, weatherConditionImageView.getId());
        upperTempLayoutParams.addRule(ALIGN_BOTTOM, weatherConditionImageView.getId());
        upperTempLayoutParams.leftMargin = marginOuter;
        upperTempContentLayout.setLayoutParams(upperTempLayoutParams);
        upperContentLayout.addView(upperTempContentLayout);

        // upper temp inner layout
        upperTempInnerContentLayout = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams upperTempInnerLayoutParams = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        upperTempInnerLayoutParams.addRule(CENTER_IN_PARENT);
        upperTempInnerContentLayout.setLayoutParams(upperTempInnerLayoutParams);
        upperTempContentLayout.addView(upperTempInnerContentLayout);

        // current temp
        currentTempTextView = new TextView(getContext());
        currentTempTextView.setId(1384174);
        currentTempTextView.setGravity(Gravity.CENTER);
        currentTempTextView.setSingleLine();
        RelativeLayout.LayoutParams currentTempParams = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        currentTempParams.addRule(CENTER_HORIZONTAL);
        currentTempTextView.setLayoutParams(currentTempParams);
        upperTempInnerContentLayout.addView(currentTempTextView);

        // lowHigh temp
        lowHighTempTextView = new TextView(getContext());
        lowHighTempTextView.setId(38417324);
        lowHighTempTextView.setGravity(Gravity.CENTER);
        lowHighTempTextView.setSingleLine();
        RelativeLayout.LayoutParams lowHighParams = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lowHighParams.addRule(BELOW, currentTempTextView.getId());
        lowHighParams.addRule(CENTER_HORIZONTAL);
        lowHighParams.topMargin = marginOuter;
        lowHighTempTextView.setLayoutParams(lowHighParams);
        upperTempInnerContentLayout.addView(lowHighTempTextView);

        // city name
        cityNameTextView = new TextView(getContext());
        cityNameTextView.setId(4311474);
        cityNameTextView.setGravity(Gravity.CENTER);
        cityNameTextView.setSingleLine();
        RelativeLayout.LayoutParams cityNameParams = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        cityNameParams.addRule(CENTER_HORIZONTAL);
        cityNameParams.addRule(BELOW, upperContentLayout.getId());
        cityNameParams.leftMargin = marginOuter;
        cityNameParams.rightMargin = marginOuter;
        cityNameTextView.setLayoutParams(cityNameParams);
        innerContentLayout.addView(cityNameTextView);

        // local time
        localTimeTextView = new TextView(getContext());
        localTimeTextView.setGravity(Gravity.CENTER);
        localTimeTextView.setSingleLine();
        RelativeLayout.LayoutParams localTimeParams = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        localTimeParams.addRule(CENTER_HORIZONTAL);
        localTimeParams.addRule(BELOW, cityNameTextView.getId());
        localTimeParams.leftMargin = marginOuter;
        localTimeParams.rightMargin = marginOuter;
        localTimeTextView.setLayoutParams(localTimeParams);
        innerContentLayout.addView(localTimeTextView);

        // test
        weatherConditionImageView.setImageDrawable(new RecyclingBitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.m_icon_weather_07_tornado)));
        currentTempTextView.setText("14°");
        lowHighTempTextView.setText("7°/19°");
        cityNameTextView.setText("Daegu");
        localTimeTextView.setText("14:11:56");

        innerContentLayout.setBackgroundColor(Color.BLUE);
        upperContentLayout.setBackgroundColor(Color.LTGRAY);
        weatherConditionImageView.setBackgroundColor(Color.CYAN);
        upperTempContentLayout.setBackgroundColor(Color.YELLOW);
        upperTempInnerContentLayout.setBackgroundColor(Color.RED);
        currentTempTextView.setBackgroundColor(Color.GREEN);
        lowHighTempTextView.setBackgroundColor(Color.MAGENTA);
        cityNameTextView.setBackgroundColor(Color.RED);
        localTimeTextView.setBackgroundColor(Color.YELLOW);
    }

    @Override
    protected void processLoading() throws JSONException {
        super.processLoading();
        if (getPanelDataObject().has(WEATHER_DATA_IS_USING_CURRENT_LOCATION)) {
            // 기본은 현재위치 사용
            isUsingCurrentLocation = getPanelDataObject().getBoolean(WEATHER_DATA_IS_USING_CURRENT_LOCATION);
        }
        if (getPanelDataObject().has(WEATHER_DATA_IS_DISPLAYING_LOCAL_TIME)) {
            // 기본은 로컬 시간 사용
            isDisplayingLocaltime = getPanelDataObject().getBoolean(WEATHER_DATA_IS_DISPLAYING_LOCAL_TIME);
        }
        if (getPanelDataObject().has(WEATHER_DATA_TEMP_CELSIUS)) {
            isUsingCelsius = getPanelDataObject().getBoolean(WEATHER_DATA_TEMP_CELSIUS);
        } else {
            // 미국만 fahrenheit 사용
            isUsingCelsius = !getResources().getConfiguration().locale.getCountry().equals("US");
        }
        if (getPanelDataObject().has(WEATHER_DATA_SELECTED_WEATHER_LOCATION_INFO)) {
            Type type = new TypeToken<MNWeatherLocationInfo>(){}.getType();
            selectedLocationInfo = new Gson().fromJson(getPanelDataObject().getString(WEATHER_DATA_SELECTED_WEATHER_LOCATION_INFO), type);
        }

        // 나중에 비동기 처리 필요
        updateUI();
    }

    @Override
    protected void updateUI() {
        super.updateUI();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
