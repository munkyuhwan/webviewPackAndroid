package com.WebviewPack.SNSLogin.CustomKakaoLogin;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.facebook.CallbackManager;
import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;

public class SetKakaoLogin {
    CallbackManager callbackManager;
    private SessionCallback callback;

    private WebView mWebview;
    private Activity mActivity;
    private ConstraintLayout mConstraintLayout;

    public SetKakaoLogin(WebView webView, Activity activity, ConstraintLayout constraintLayout) {
        this.mWebview = webView;
        this.mActivity = activity;
        this.mConstraintLayout = constraintLayout;
        callback = new SessionCallback();


    }

    public void doKakaoLogin() {
        Session.getCurrentSession().addCallback(callback);
        final LoginButton kakaoLogin = new LoginButton(this.mActivity);
        this.mConstraintLayout.addView(kakaoLogin);
        kakaoLogin.setVisibility(View.GONE);
        kakaoLogin.performClick();
    }

    class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {

            UserManagement.requestMe(new MeResponseCallback() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    Log.e("UserProfile", "================================================================");
                    String message = "failed to get user info. msg=" + errorResult;

                    ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                    if (result == ErrorCode.CLIENT_ERROR_CODE) {
                        //에러로 인한 로그인 실패
                        // finish();
                    } else {
                        //redirectMainActivity();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    Log.e("UserProfile", "================================================================");
                    Log.e("UserProfile", "sesson closed");

                }

                @Override
                public void onNotSignedUp() {
                    Log.e("UserProfile", "================================================================");
                    Log.e("UserProfile", "sesson sing up");

                }

                @Override
                public void onSuccess(UserProfile userProfile) {
                    //로그인에 성공하면 로그인한 사용자의 일련번호, 닉네임, 이미지url등을 리턴합니다.
                    //사용자 ID는 보안상의 문제로 제공하지 않고 일련번호는 제공합니다.
                    Log.e("UserProfile", "================================================================");
                    mWebview.loadUrl("javascript:sns_login_return(\""+userProfile.getId()+"\");");

                }
            });

        }
        // 세션 실패시
        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            exception.printStackTrace();
        }
    }

}
