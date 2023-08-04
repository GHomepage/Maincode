
package com.baidu.duer.dcs.http;


import com.baidu.dcs.okhttp3.MediaType;

import static com.baidu.duer.dcs.http.HttpConfig.ContentTypes.APPLICATION_AUDIO;
import static com.baidu.duer.dcs.http.HttpConfig.ContentTypes.APPLICATION_JSON;

public class OkHttpMediaType {
    // json类型
    public static final MediaType MEDIA_JSON_TYPE = MediaType.parse(APPLICATION_JSON);
    // 数据流类型
    public static final MediaType MEDIA_STREAM_TYPE = MediaType.parse(APPLICATION_AUDIO);
}