
package com.baidu.duer.dcs.oauth.api;

import android.content.Context;

import com.baidu.duer.dcs.util.PreferenceUtil;

public class OauthPreferenceUtil extends PreferenceUtil {
    public static final String BAIDU_OAUTH_CONFIG = "baidu_oauth_config";


    public static void put(Context context, String key, Object object) {
        put(context, BAIDU_OAUTH_CONFIG, key, object);
    }

    public static Object get(Context context, String key, Object defaultObject) {
        return get(context, BAIDU_OAUTH_CONFIG, key, defaultObject);
    }
    
    public static void clear(Context context) {
        clear(context, BAIDU_OAUTH_CONFIG);
    }

    public static void setAccessToken(Context context, String value) {
        put(context, OauthConfig.PrefenenceKey.SP_ACCESS_TOKEN, value);
    }

    public static String getAccessToken(Context context) {
        return (String) get(context, OauthConfig.PrefenenceKey.SP_ACCESS_TOKEN, "");
    }

    public static void setExpires(Context context, long value) {
        put(context, OauthConfig.PrefenenceKey.SP_EXPIRE_SECONDS, value);
    }

    public static long getExpires(Context context) {
        return (long) get(context, OauthConfig.PrefenenceKey.SP_EXPIRE_SECONDS, 0L);
    }

    public static void setCreateTime(Context context, long value) {
        put(context, OauthConfig.PrefenenceKey.SP_CREATE_TIME, value);
    }

    public static long getCreateTime(Context context) {
        return (long) get(context, OauthConfig.PrefenenceKey.SP_CREATE_TIME, 0L);
    }

    public static void clearAllOauth(Context context) {
        clear(context);
    }
}