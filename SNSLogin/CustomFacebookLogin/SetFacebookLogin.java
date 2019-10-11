package com.WebviewPack.SNSLogin.CustomFacebookLogin;

import android.app.Activity;
import android.webkit.WebView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.webviewpackaging.R;

import java.util.Arrays;

public class SetFacebookLogin {
    private CallbackManager callbackManager;
    private Activity mActivity;
    private WebView mWebview;

    public SetFacebookLogin(Activity activity, WebView webView) {
        this.mActivity = activity;
        this.mWebview = webView;
    }

    public void init() {
        callbackManager = CallbackManager.Factory.create();

        FacebookSdk.setApplicationId( this.mActivity.getResources().getString(R.string.facebook_app_id));
        FacebookSdk.sdkInitialize(this.mActivity);
        AppEventsLogger.activateApp(this.mActivity);
        facebookCallback();
    }

    public void doFBLogin() {
        LoginManager.getInstance().logInWithReadPermissions(this.mActivity, Arrays.asList("public_profile"));
    }


    public void facebookCallback() {

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        mWebview.loadUrl("javascript:sns_login_return(\""+loginResult.getAccessToken().getUserId()+"\",\"fb\");");
                    }

                    @Override
                    public void onCancel() {
                        // App code

                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        exception.printStackTrace();
                    }
                });

    }
}
