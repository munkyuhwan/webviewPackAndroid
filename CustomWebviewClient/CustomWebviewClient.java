package com.WebviewPack.CustomWebviewClient;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

/**
 * Created by Moon on 2018. 10. 3..
 */
public class CustomWebviewClient extends WebViewClient {

    private Activity mActivity;
    public CustomWebviewClient(Activity activity) {
        this.mActivity = activity;
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d("tag","shoulde override loading");
        if (url.startsWith("intent:")) {
            try {
                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                Intent existPackage = mActivity.getPackageManager().getLaunchIntentForPackage(intent.getPackage());
                if (existPackage != null) {
                    mActivity.startActivity(intent);
                } else {
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                    marketIntent.setData(Uri.parse("market://details?id=" + intent.getPackage()));
                    mActivity.startActivity(marketIntent);
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            view.loadUrl(url);

        }

        return true;

    }
}
