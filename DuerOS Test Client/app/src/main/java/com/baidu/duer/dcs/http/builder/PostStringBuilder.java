
package com.baidu.duer.dcs.http.builder;

import com.baidu.dcs.okhttp3.MediaType;
import com.baidu.duer.dcs.http.request.PostStringRequest;
import com.baidu.duer.dcs.http.request.RequestCall;


public class PostStringBuilder extends OkHttpRequestBuilder<PostStringBuilder> {
    // 内容
    private String content;
    // 类型
    private MediaType mediaType;

    public PostStringBuilder content(String content) {
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostStringRequest(url, tag, params, headers, content, mediaType, id).build();
    }
}