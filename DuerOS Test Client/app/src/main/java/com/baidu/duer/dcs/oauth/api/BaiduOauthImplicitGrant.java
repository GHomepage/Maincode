
package com.baidu.duer.dcs.oauth.api;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieSyncManager;

import com.baidu.duer.dcs.oauth.api.BaiduDialog.BaiduDialogListener;
import com.baidu.duer.dcs.util.CommonUtil;
import com.baidu.duer.dcs.util.LogUtil;


public class BaiduOauthImplicitGrant implements Parcelable {
    private static final String LOG_TAG = "BaiduOauth";
    public static final String CANCEL_URI = "bdconnect://cancel";
    // 百度Oauth授权回调需要在DUEROS开放平台的控制平台
    // 应用编辑-->>OAUTH CONFIG URL的链接地址-->>授权回调页-->>安全设置-->>授权回调页
    // 需要注意
    public static final String SUCCESS_URI = "bdconnect://success";
    private static final String OAUTHORIZE_URL = "https://openapi.baidu.com/oauth/2.0/authorize";
    // 账号登录
    private static final String DISPLAY_STRING = "mobile";
    // 扫码登录
    // private static final String DISPLAY_STRING = "popup";
    private static final String[] DEFAULT_PERMISSIONS = {"basic"};
    private static final String KEY_CLIENT_ID = "clientId";
    // 应用注册的api key信息
    private String cliendId;
    private AccessTokenManager accessTokenManager;

    public BaiduOauthImplicitGrant(String clientId, Context context) {
        if (clientId == null) {
            throw new IllegalArgumentException("apiKey信息必须提供！");
        }
        this.cliendId = clientId;
        init(context);
    }


    public BaiduOauthImplicitGrant(Parcel in) {
        Bundle bundle = Bundle.CREATOR.createFromParcel(in);
        this.cliendId = bundle.getString(KEY_CLIENT_ID);
        this.accessTokenManager = AccessTokenManager.CREATOR.createFromParcel(in);
    }


    public void init(Context context) {
        if (context.checkCallingOrSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.w(LOG_TAG, "App miss permission android.permission.ACCESS_NETWORK_STATE! "
                    + "Some mobile's WebView don't display page!");
        }
        this.accessTokenManager = new AccessTokenManager(context);
        this.accessTokenManager.initToken();
    }

    public void authorize(Activity activity,
                          boolean isForceLogin,
                          boolean isConfirmLogin,
                          final BaiduDialogListener listener) {
        this.authorize(activity, null, isForceLogin, isConfirmLogin, listener);
    }


    private void authorize(Activity activity,
                           String[] permissions,
                           boolean isForceLogin,
                           boolean isConfirmLogin,
                           final BaiduDialogListener listener) {
        if (this.isSessionValid()) {
            listener.onComplete(new Bundle());
            return;
        }

        // 使用匿名的BaiduDialogListener对listener进行了包装，并进行一些存储token信息和当前登录用户的逻辑，
        // 外部传进来的listener信息不需要在进行存储相关的逻辑
        this.authorize(activity,
                permissions,
                isForceLogin,
                isConfirmLogin,
                new BaiduDialogListener() {
                    @Override
                    public void onError(BaiduDialogError e) {
                        LogUtil.d(LOG_TAG, "DialogError " + e);
                        listener.onError(e);
                    }

                    @Override
                    public void onComplete(Bundle values) {
                        // 存储相应的token信息
                        getAccessTokenManager().storeToken(values);
                        // 完成授权操作，使用listener进行回调，eg。跳转到其他的activity
                        listener.onComplete(values);
                    }

                    @Override
                    public void onCancel() {
                        LogUtil.d(LOG_TAG, "login cancel");
                        listener.onCancel();
                    }

                    @Override
                    public void onBaiduException(BaiduException e) {
                        Log.d(LOG_TAG, "BaiduException : " + e);
                        listener.onBaiduException(e);
                    }
                }, SUCCESS_URI, "token");
    }


    private void authorize(Activity activity,
                           String[] permissions,
                           boolean isForceLogin,
                           boolean isConfirmLogin,
                           final BaiduDialogListener listener,
                           String redirectUrl, String responseType) {
        CookieSyncManager.createInstance(activity);
        Bundle params = new Bundle();
        params.putString("client_id", this.cliendId);
        params.putString("redirect_uri", redirectUrl);
        params.putString("response_type", responseType);
        params.putString("display", DISPLAY_STRING);
        if (isForceLogin) {
            params.putString("force_login", "1");
        }
        if (isConfirmLogin) {
            params.putString("confirm_login", "1");
        }
        if (permissions == null) {
            permissions = DEFAULT_PERMISSIONS;
        }
        if (permissions != null && permissions.length > 0) {
            String scope = TextUtils.join(" ", permissions);
            params.putString("scope", scope);
        }
        params.putString("qrcode", "1");
        String url = OAUTHORIZE_URL + "?" + CommonUtil.encodeUrl(params);
        LogUtil.d(LOG_TAG, "url:" + url);
        if (activity.checkCallingOrSelfPermission(Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            CommonUtil.showAlert(activity, "没有权限", "应用需要访问互联网的权限");
        } else {
            new BaiduDialog(activity, url, listener).show();
        }
    }


    public void clearAccessToken() {
        if (this.accessTokenManager != null) {
            this.accessTokenManager.clearToken();
            this.accessTokenManager = null;
        }
    }


    public boolean isSessionValid() {
        return this.accessTokenManager.isSessionValid();
    }


    public AccessTokenManager getAccessTokenManager() {
        return this.accessTokenManager;
    }

    public String getAccessToken() {
        return this.accessTokenManager.getAccessToken();
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_CLIENT_ID, this.cliendId);
        bundle.writeToParcel(dest, flags);
        this.accessTokenManager.writeToParcel(dest, flags);
    }

    public static final Creator<BaiduOauthImplicitGrant> CREATOR = new Creator<BaiduOauthImplicitGrant>() {
        public BaiduOauthImplicitGrant createFromParcel(Parcel in) {
            return new BaiduOauthImplicitGrant(in);
        }

        public BaiduOauthImplicitGrant[] newArray(int size) {
            return new BaiduOauthImplicitGrant[size];
        }
    };
}