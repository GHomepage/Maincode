
package com.baidu.duer.dcs.http.builder;

import com.baidu.duer.dcs.http.request.RequestCall;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class OkHttpRequestBuilder<T extends OkHttpRequestBuilder> {
    // 请求url
    protected String url;
    // 请求tag
    protected Object tag;
    // 请求的headers
    protected Map<String, String> headers;
    // 请求的参数集合
    protected Map<String, String> params;
    // 请求的表识id
    protected int id;

    public T id(int id) {
        this.id = id;
        return (T) this;
    }

    public T url(String url) {
        this.url = url;
        return (T) this;
    }


    public T tag(Object tag) {
        this.tag = tag;
        return (T) this;
    }

    public T headers(Map<String, String> headers) {
        this.headers = headers;
        return (T) this;
    }

    public T addHeader(String key, String val) {
        if (this.headers == null) {
            headers = new LinkedHashMap<>();
        }
        headers.put(key, val);
        return (T) this;
    }

    public abstract RequestCall build();
}