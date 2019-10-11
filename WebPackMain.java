package com.WebviewPack;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.WebviewPack.CustomJavascript.WebviewJavascriptInterface;
import com.WebviewPack.EtcUtils.BackPressHandler;
import com.WebviewPack.EtcUtils.SetWebviewPermission;
import com.WebviewPack.SNSLogin.CustomFacebookLogin.SetFacebookLogin;
import com.WebviewPack.SNSLogin.CustomKakaoLogin.SetKakaoLogin;
import com.webviewpackaging.R;

import java.util.ArrayList;

public class WebPackMain extends AppCompatActivity implements WebviewJavascriptInterface {

    Activity mMainActivity;
    private WebView mWebview;
    private String url;
    private BackPressHandler backPressCloseHandler;


    private int REQUEST_PERMISSION_CODE = 998;

    private static final int RC_FILE_CHOOSE = 2833;
    private ValueCallback<Uri[]> mUploadMsg = null;
    private Uri outputFileUri;

    private SetKakaoLogin setKakaoLogin;
    private SetFacebookLogin setFacebookLogin;

    public WebPackMain(Activity mainActivity) {
        this.mMainActivity = mainActivity;
    }
    private ConstraintLayout mConstraintLayout;
    private CustomWebviewSettings settings;

    private SetWebviewPermission setPermission;

    public WebPackMain init(String url, ConstraintLayout constraintLayout) {
        this.url = url;
        this.mConstraintLayout = constraintLayout;
        backPressCloseHandler = new BackPressHandler(mMainActivity); //뒤로가기 버튼 종료 클래스
        setPermission = new SetWebviewPermission(mMainActivity, REQUEST_PERMISSION_CODE);

        mWebview = new WebView(this.mMainActivity);
        this.mConstraintLayout.addView(mWebview);
        settings = new CustomWebviewSettings(this.mWebview,this);//웹뷰 설정 세팅
        settings.doWebviewSetting();

        //getFCMToken();// FCM토큰 받아오기

        return this;
    }

    public void backPressClose() {
        if (mWebview.canGoBack()) {
            if (mWebview.getUrl().equals(mMainActivity.getString(R.string.webview_url))) {
                backPressCloseHandler.onBackPressed();
            }else {
                mWebview.goBack();
            }
        }else {
            backPressCloseHandler.onBackPressed();
        }
    }

    public WebPackMain addPermissionList(String permissionList) {
        setPermission.addPermission(permissionList);
        return this;
    }

    public WebPackMain requestPermissionList() {
        setPermission.checkPermissions();
        return this;
    }

    public void startLoadingWebview() {
        mWebview.loadUrl(url);//웹뷰 로드

    }

    public WebPackMain kakaoSetting(boolean setBool) {
        if (setBool) {
            Log.d("tag","set kakao");
            kakaoSetting(this.mConstraintLayout);
        }
        return this;
    }

    public WebPackMain facebookSetting(boolean setBool) {
        if (setBool) {
            facebookSetting();
        }
        return this;
    }


    private void kakaoSetting(ConstraintLayout constraintLayout) {
        setKakaoLogin = new SetKakaoLogin(mWebview, this.mMainActivity, constraintLayout);
    }

    private void facebookSetting() {
        setFacebookLogin = new SetFacebookLogin(mMainActivity, mWebview);
        setFacebookLogin.init();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        */
        if (requestCode == RC_FILE_CHOOSE && mUploadMsg != null) {
            //파일 선택후 처리
            Uri[]result = null;
            if ( data != null || resultCode == RESULT_OK ) {
                result = new Uri[1];
                if (data != null) {
                    result[0] = data.getData();
                } else {
                    result[0] = outputFileUri;
                }
                mUploadMsg.onReceiveValue(result);
                mUploadMsg = null;
                return;
            }else {
                mUploadMsg.onReceiveValue(result);
                mUploadMsg = null;
                return;
            }
        }

    }


    @Override
    public void fcmGo() {

    }

    @Override
    public void kakaoLogin() {
        setKakaoLogin.doKakaoLogin();
    }

    @Override
    public void fbLogin() {
        setFacebookLogin.doFBLogin();
    }

    public void setmUploadMsg(ValueCallback<Uri[]> mUploadMsg) {
        this.mUploadMsg = mUploadMsg;
    }

    public void setOutputFileUri(Uri outputFileUri) {
        this.outputFileUri = outputFileUri;
    }

    public static int getRcFileChoose() {
        return RC_FILE_CHOOSE;
    }

    public ValueCallback<Uri[]> getmUploadMsg() {
        return mUploadMsg;
    }

    public Uri getOutputFileUri() {
        return outputFileUri;
    }
}
