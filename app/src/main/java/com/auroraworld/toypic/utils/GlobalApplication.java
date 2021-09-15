package com.auroraworld.toypic.utils;

import android.app.Application;

import com.auroraworld.toypic.R;
import com.kakao.sdk.common.KakaoSdk;

public class GlobalApplication extends Application {
    private static GlobalApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        KakaoSdk.init(this, getString(R.string.kakao_native_appkey));
    }
}
