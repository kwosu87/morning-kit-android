package com.yooiistudios.morningkit.common;

import org.robolectric.bytecode.ClassInfo;
import org.robolectric.bytecode.Setup;

/**
 * Created by StevenKim on 2013. 11. 5..
 *
 * EnhancedSetup
 *  Admob, Analytics 테스트를 지원하는 코드
 */
public class EnhancedSetup extends Setup {
    @Override
    public boolean isFromAndroidSdk(ClassInfo classInfo) {
        return super.isFromAndroidSdk(classInfo)
                || classInfo.getName().startsWith("com.google.ads.")
                || classInfo.getName().startsWith("com.google.analytics.");
    }
}
