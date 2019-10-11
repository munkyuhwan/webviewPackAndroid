package com.WebviewPack;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.WebviewPack.CustomJavascript.CustomJavascriptInterface;
import com.WebviewPack.CustomWebviewClient.CustomWebChrome;
import com.WebviewPack.CustomWebviewClient.CustomWebviewClient;

public class CustomWebviewSettings {
    private WebView mWebview;
    private WebPackMain mMainActivity;

    public CustomWebviewSettings(WebView webView, WebPackMain mainActivity) {
        this.mWebview = webView;
        this.mMainActivity = mainActivity;
    }

    public void doWebviewSetting() {

        mWebview.addJavascriptInterface(new CustomJavascriptInterface(this.mMainActivity ), "Android");
        mWebview.setWebChromeClient(new CustomWebChrome(this.mMainActivity, this.mMainActivity));

        mWebview.setWebViewClient(new CustomWebviewClient(mMainActivity));
        mWebview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebview.getSettings().setAppCacheEnabled(true);

        mWebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebview.getSettings().setSupportMultipleWindows(true);
        mWebview.getSettings().setAllowFileAccess(true);
        mWebview.getSettings().setDomStorageEnabled(true);
        mWebview.getSettings().setPluginState(WebSettings.PluginState.ON);
        mWebview.getSettings().setAllowContentAccess(true);
        mWebview.getSettings().setAllowFileAccessFromFileURLs(true);
        mWebview.getSettings().setAllowUniversalAccessFromFileURLs(true);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().setMediaPlaybackRequiresUserGesture(false);

        mWebview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mWebview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebview.getSettings().setEnableSmoothTransition(true);

        mWebview.setWebContentsDebuggingEnabled(true);

        // mWebview.getSettings().setGeolocationDatabasePath(getFilesDir().getPath());
        mWebview.getSettings().setGeolocationEnabled(true);
        mWebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebview.getSettings().setSupportMultipleWindows(false);

        mWebview.getSettings().setAppCacheEnabled(false);
        mWebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        mWebview.getSettings().setDatabaseEnabled(true);
        mWebview.getSettings().setDomStorageEnabled(true);
        mWebview.getSettings().setAppCacheEnabled(true);

    }
}
