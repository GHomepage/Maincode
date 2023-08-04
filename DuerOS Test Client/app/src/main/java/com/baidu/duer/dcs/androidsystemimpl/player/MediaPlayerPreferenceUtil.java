package com.baidu.duer.dcs.androidsystemimpl.player;

import android.content.Context;

import com.baidu.duer.dcs.util.PreferenceUtil;


public class MediaPlayerPreferenceUtil extends PreferenceUtil {
    // 保存到的文件名字
    private static final String BAIDU_MEDIA_CONFIG = "baidu_media_config";

    public static void put(Context context, String key, Object object) {
        put(context, BAIDU_MEDIA_CONFIG, key, object);
    }


    public static Object get(Context context, String key, Object defaultObject) {
        return get(context, BAIDU_MEDIA_CONFIG, key, defaultObject);
    }
}
