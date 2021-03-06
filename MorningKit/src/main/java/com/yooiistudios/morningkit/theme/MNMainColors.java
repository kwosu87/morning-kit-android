package com.yooiistudios.morningkit.theme;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import com.yooiistudios.morningkit.setting.theme.themedetail.MNThemeType;
import com.yooiistudios.morningkit.theme.font.MNTranslucentFont;
import com.yooiistudios.morningkit.theme.font.MNTranslucentFontType;

/**
 * Created by StevenKim in MorningKit from Yooii Studios Co., LTD. on 2013. 12. 19.
 *
 * MNColor
 *  각종 컬러값을 얻을 수 있는 클래스
 */
public class MNMainColors {
    private MNMainColors() { throw new AssertionError("You MUST not create this class!"); }

    /**
     * Background Color
     */
    public static int getBackwardBackgroundColor(MNThemeType themeType) {
        switch (themeType) {
            case WATER_LILY:
            case TRANQUILITY_BACK_CAMERA:
            case REFLECTION_FRONT_CAMERA:
            case PHOTO:
                return Color.TRANSPARENT;

            case MODERNITY_WHITE:
                return Color.parseColor("#e8e8e8");

            case SLATE_GRAY:
                return Color.parseColor("#333333");

            case CELESTIAL_SKY_BLUE:
                return Color.parseColor("#049ebd");

            case PASTEL_GREEN:
                return Color.parseColor("#ffffff");

            case COOL_NAVY:
                return Color.parseColor("#212931");

            case MINT_PINK:
                return Color.parseColor("#d7ede0");

            default: throw new IndexOutOfBoundsException("Undefined Enumeration Index");
        }
    }

    public static int getButtonLayoutBackgroundColor(MNThemeType themeType) {
        switch (themeType) {
            case WATER_LILY:
            case TRANQUILITY_BACK_CAMERA:
            case REFLECTION_FRONT_CAMERA:
            case PHOTO:
            case MODERNITY_WHITE:
            case SLATE_GRAY:
                return Color.parseColor("#CC000000");

            case CELESTIAL_SKY_BLUE:
                return Color.parseColor("#E5043f4b");

            case PASTEL_GREEN:
                return Color.parseColor("#E55ab38c"); // 아직 더 조정할 필요가 있음

            case COOL_NAVY:
                return Color.parseColor("#E52c85b3");

            case MINT_PINK:
                return Color.parseColor("#E5f59599");

            default: throw new IndexOutOfBoundsException("Undefined Enumeration Index");
        }
    }

    /**
     * Font Color
     */
    public static int getMainFontColor(MNThemeType themeType, Context context) {
        switch (themeType) {
            case TRANQUILITY_BACK_CAMERA:
            case REFLECTION_FRONT_CAMERA:
            case PHOTO:
                if (MNTranslucentFont.getCurrentFontType(context) == MNTranslucentFontType.WHITE) {
                    return Color.parseColor("#ffffff");
                } else {
                    return Color.parseColor("#333333");
                }

            case WATER_LILY:
                return Color.parseColor("#333333");

            case MODERNITY_WHITE:
                return Color.parseColor("#252525");

            case SLATE_GRAY:
                return Color.parseColor("#ffffff");

            case CELESTIAL_SKY_BLUE:
                return Color.parseColor("#ffffff");

            case PASTEL_GREEN:
                return Color.parseColor("#5ab38c");

            case COOL_NAVY:
                return Color.parseColor("#30acea");

            case MINT_PINK:
                return Color.parseColor("#f59599");

            default: throw new IndexOutOfBoundsException("Undefined Enumeration Index");
        }
    }

    public static int getSubFontColor(MNThemeType themeType, Context context) {
        switch (themeType) {
            case TRANQUILITY_BACK_CAMERA:
            case REFLECTION_FRONT_CAMERA:
            case PHOTO:
                if (MNTranslucentFont.getCurrentFontType(context) == MNTranslucentFontType.WHITE) {
//                    return Color.parseColor("#cccccc");
                    return Color.parseColor("#ffffff");
                } else {
//                    return Color.parseColor("#666666");
                    return Color.parseColor("#333333");
                }

            case WATER_LILY:
//                return Color.parseColor("#918f8f");
                return Color.parseColor("#333333");

            case MODERNITY_WHITE:
//                return Color.parseColor("#a5a5a5");
                return Color.parseColor("#767676");

            case SLATE_GRAY:
                return Color.parseColor("#979797");

            case CELESTIAL_SKY_BLUE:
                return Color.parseColor("#e4e4e4");

            case PASTEL_GREEN:
                return Color.parseColor("#797979");

            case COOL_NAVY:
                return Color.parseColor("#ffffff"); // same with PASTEL_GREEN

            case MINT_PINK:
                return Color.parseColor("#b49e93");

            default: throw new IndexOutOfBoundsException("Undefined Enumeration Index");
        }
    }

    /**
     * Alarm
     */

    // 나중에 알람만 다른 색을 쓰게 될 경우를 대비
    public static int getAlarmMainFontColor(MNThemeType themeType, Context context) {
        return getMainFontColor(themeType, context);
    }

    public static int getAlarmAddTextFontColor(MNThemeType themeType, Context context) {
        if (themeType == MNThemeType.CELESTIAL_SKY_BLUE) {
            return Color.parseColor("#043f4b");
        } else if (themeType == MNThemeType.PASTEL_GREEN) {
            return Color.parseColor("#797979");
        } else if (themeType == MNThemeType.COOL_NAVY) {
            return getMainFontColor(MNThemeType.SLATE_GRAY, context);
        } else if (themeType == MNThemeType.MINT_PINK) {
            return getSubFontColor(MNThemeType.MINT_PINK, context);
        } else {
            return getAlarmMainFontColor(themeType, context);
        }
    }

    public static int getAlarmSubFontColor(MNThemeType themeType, Context context) {
        switch (themeType) {
            case TRANQUILITY_BACK_CAMERA:
            case REFLECTION_FRONT_CAMERA:
            case PHOTO:
                if (MNTranslucentFont.getCurrentFontType(context) == MNTranslucentFontType.WHITE) {
                    return Color.parseColor("#cccccc");
                } else {
                    return Color.parseColor("#666666");
                }

            case WATER_LILY:
                return Color.parseColor("#666666");

            case PASTEL_GREEN:
                return Color.parseColor("#c1c1c1");

            case COOL_NAVY:
                return Color.parseColor("#c1c1c1"); // same with PASTEL_GREEN

            case MINT_PINK:
                return Color.parseColor("#c1c1c1");

            default:
                return getSubFontColor(themeType, context);
        }
    }

    /**
     * Panel
     */
    public static int getWeatherLowHighTextColor(MNThemeType themeType, Context context) {
        switch (themeType) {
            case WATER_LILY:
            case TRANQUILITY_BACK_CAMERA:
            case REFLECTION_FRONT_CAMERA:
            case PHOTO:
            case MODERNITY_WHITE:
            case SLATE_GRAY:
            case CELESTIAL_SKY_BLUE:
                return getMainFontColor(themeType, context);

            case PASTEL_GREEN:
            case COOL_NAVY:
            case MINT_PINK:
                return getSubFontColor(themeType, context);

            default: throw new IndexOutOfBoundsException("Undefined Enumeration Index");
        }
    }

    public static int getWeatherConditionColor(MNThemeType themeType, Context context) {
        switch (themeType) {
            case WATER_LILY:
            case TRANQUILITY_BACK_CAMERA:
            case REFLECTION_FRONT_CAMERA:
            case PHOTO:
            case MODERNITY_WHITE:
            case SLATE_GRAY:
            case CELESTIAL_SKY_BLUE:
                return getMainFontColor(themeType, context);

            case PASTEL_GREEN:
            case COOL_NAVY:
            case MINT_PINK:
                return getSubFontColor(themeType, context);

            default: throw new IndexOutOfBoundsException("Undefined Enumeration Index");
        }
    }

    public static int getCalendarItemDividerColor(MNThemeType themeType, Context context) {
        switch (themeType) {
            case WATER_LILY:
            case TRANQUILITY_BACK_CAMERA:
            case REFLECTION_FRONT_CAMERA:
            case PHOTO:
            case MODERNITY_WHITE:
            case SLATE_GRAY:
            case CELESTIAL_SKY_BLUE:
                return getSubFontColor(themeType, context);

            case PASTEL_GREEN:
            case COOL_NAVY:
            case MINT_PINK:
                return Color.parseColor("#c1c1c1");

            default: throw new IndexOutOfBoundsException("Undefined Enumeration Index");
        }
    }

    public static int getCalendarDividerItemDividerColor(MNThemeType themeType, Context context) {
        switch (themeType) {
            case WATER_LILY:
            case TRANQUILITY_BACK_CAMERA:
            case REFLECTION_FRONT_CAMERA:
            case PHOTO:
            case MODERNITY_WHITE:
            case SLATE_GRAY:
            case CELESTIAL_SKY_BLUE:
            case PASTEL_GREEN:
            case COOL_NAVY:
            case MINT_PINK:
                return getMainFontColor(themeType, context);

            default: throw new IndexOutOfBoundsException("Undefined Enumeration Index");
        }
    }

    public static int getQuoteContentTextColor(MNThemeType themeType, Context context) {
        switch (themeType) {
            case WATER_LILY:
            case TRANQUILITY_BACK_CAMERA:
            case REFLECTION_FRONT_CAMERA:
            case PHOTO:
            case MODERNITY_WHITE:
            case SLATE_GRAY:
            case CELESTIAL_SKY_BLUE:
                return getMainFontColor(themeType, context);

            case PASTEL_GREEN:
            case COOL_NAVY:
            case MINT_PINK:
                return getSubFontColor(themeType, context);

            default: throw new IndexOutOfBoundsException("Undefined Enumeration Index");
        }
    }

    public static int getQuoteAuthorTextColor(MNThemeType themeType, Context context) {
        switch (themeType) {
            case WATER_LILY:
            case TRANQUILITY_BACK_CAMERA:
            case REFLECTION_FRONT_CAMERA:
            case PHOTO:
            case MODERNITY_WHITE:
            case SLATE_GRAY:
            case CELESTIAL_SKY_BLUE:
                return getSubFontColor(themeType, context);

            case PASTEL_GREEN:
            case COOL_NAVY:
            case MINT_PINK:
                return getMainFontColor(themeType, context);

            default: throw new IndexOutOfBoundsException("Undefined Enumeration Index");
        }
    }
}
