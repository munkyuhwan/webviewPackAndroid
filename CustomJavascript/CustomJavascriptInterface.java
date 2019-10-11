package com.WebviewPack.CustomJavascript;

import android.webkit.JavascriptInterface;


/**
 * Created by Moon on 2018. 4. 6..
 */

public class CustomJavascriptInterface {

    private WebviewJavascriptInterface mInterface;

    public CustomJavascriptInterface(WebviewJavascriptInterface mInterface) {
        this.mInterface = mInterface;
    }

    @JavascriptInterface
    public void get_fcm_token() {
        mInterface.fcmGo();
    }

    @JavascriptInterface
    public void get_geolocation() {
       // mInterface.getLocation();
    }

    @JavascriptInterface
    public void kakao_login() {
        mInterface.kakaoLogin();
    }

    @JavascriptInterface
    public void fb_login() {
        mInterface.fbLogin();
    }

}
