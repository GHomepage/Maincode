
package com.baidu.duer.dcs.oauth.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import static com.baidu.duer.dcs.oauth.api.OauthPreferenceUtil.BAIDU_OAUTH_CONFIG;


public class AccessTokenManager implements Parcelable {
    // accessToken信息
    private String accessToken;
    // token过期时间
    private long expireTime = 0;
    // 当前的上下文环境
    private Context context;

    /**
     * 构建AccessTokenManager类
     *
     * @param context 当前的上下文环境，通常为××Activity.this等
     */
    public AccessTokenManager(Context context) {
        this.context = context;
        compareWithConfig();
    }

    /**
     * 通过Parcel流构建AccessTokenManager，主要用在Parcelable.Creator中
     *
     * @param source Parcel 流信息
     */
    public AccessTokenManager(Parcel source) {
        Bundle bundle = Bundle.CREATOR.createFromParcel(source);
        if (bundle != null) {
            this.accessToken = bundle.getString(OauthConfig.BundleKey.KEY_ACCESS_TOKEN);
            this.expireTime = bundle.getLong(OauthConfig.BundleKey.KEY_EXPIRE_TIME);
        }
        compareWithConfig();
    }

    /**
     * 检查当token信息与配置文件是否保持一致，若不一致则对当前的token信息进行初始化
     */
    private void compareWithConfig() {
        if (this.context == null) {
            return;
        }

        /**
         * 对配置的权限信息进行监控，保持多个AccessTokenManager对象之间的，权限信息一致。
         */
        final SharedPreferences sp = this.context.getSharedPreferences(BAIDU_OAUTH_CONFIG, Context.MODE_PRIVATE);
        sp.registerOnSharedPreferenceChangeListener(new OnSharedPreferenceChangeListener() {

            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                String acToken = sp.getString(OauthConfig.PrefenenceKey.SP_ACCESS_TOKEN, null);
                if (accessToken != null && !accessToken.equals(acToken)) {
                    initToken();
                }
            }
        });
    }

    /**
     * 从SharedPreference中读取token数据，并初步判断数据的有效性
     */
    protected void initToken() {
        this.accessToken = OauthPreferenceUtil.getAccessToken(context);
        long expires = OauthPreferenceUtil.getExpires(context);
        long createTime = OauthPreferenceUtil.getCreateTime(context);
        long current = System.currentTimeMillis();
        this.expireTime = createTime + expires;
        if (expireTime != 0 && expireTime < current) {
            clearToken();
        }
    }

    /**
     * 清楚SharedPreference中的所有数据
     */
    protected void clearToken() {
        OauthPreferenceUtil.clearAllOauth(context);
        this.accessToken = null;
        this.expireTime = 0;
    }

    /**
     * 将token信息存储到SharedPreference中
     *
     * @param values token信息的key-value形式
     */
    protected void storeToken(Bundle values) {
        if (values == null || values.isEmpty()) {
            return;
        }
        this.accessToken = values.getString("access_token");
        // expires_in 返回值为秒
        long expiresIn = Long.parseLong(values.getString("expires_in")) * 1000;
        this.expireTime = System.currentTimeMillis() + expiresIn;
        OauthPreferenceUtil.setAccessToken(context, this.accessToken);
        OauthPreferenceUtil.setCreateTime(context, System.currentTimeMillis());
        OauthPreferenceUtil.setExpires(context, expiresIn);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        if (this.accessToken != null) {
            bundle.putString(OauthConfig.BundleKey.KEY_ACCESS_TOKEN, this.accessToken);
        }
        if (this.expireTime != 0) {
            bundle.putLong(OauthConfig.BundleKey.KEY_EXPIRE_TIME, this.expireTime);
        }
        bundle.writeToParcel(dest, flags);
    }

    public static final Creator<AccessTokenManager> CREATOR = new Creator<AccessTokenManager>() {
        @Override
        public AccessTokenManager createFromParcel(Parcel source) {
            return new AccessTokenManager(source);
        }

        @Override
        public AccessTokenManager[] newArray(int size) {
            return new AccessTokenManager[size];
        }

    };

    /**
     * 判断当前的token信息是否有效
     *
     * @return true/false
     */
    protected boolean isSessionValid() {
        if (this.accessToken == null || this.expireTime == 0) {
            initToken();
        }
        return this.accessToken != null && this.expireTime != 0 && System.currentTimeMillis() < this.expireTime;
    }

    /**
     * 获取AccessToken信息
     *
     * @return accessToken
     */
    public String getAccessToken() {
        if (this.accessToken == null) {
            initToken();
        }
        return this.accessToken;
    }
}