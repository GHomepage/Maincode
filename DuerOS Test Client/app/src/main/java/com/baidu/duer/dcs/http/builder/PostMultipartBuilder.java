
package com.baidu.duer.dcs.http.builder;

import com.baidu.dcs.okhttp3.RequestBody;
import com.baidu.duer.dcs.http.request.PostMultipartRequest;
import com.baidu.duer.dcs.http.request.RequestCall;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;


public class PostMultipartBuilder extends OkHttpRequestBuilder<PostMultipartBuilder>
        implements HasParamInterface {
    private LinkedList<Multipart> multiParts = new LinkedList<>();

    @Override
    public RequestCall build() {
        return new PostMultipartRequest(url, tag, params, headers, multiParts, id).build();
    }

    @Override
    public PostMultipartBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    @Override
    public PostMultipartBuilder addParams(String key, String val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return this;
    }


    public PostMultipartBuilder multiParts(Map<String, RequestBody> map) {
        if (map != null) {
            multiParts = new LinkedList<>();
        }
        for (String k : map.keySet()) {
            this.multiParts.add(new Multipart(k, map.get(k)));
        }
        return this;
    }


    public PostMultipartBuilder addMultiPart(String name, RequestBody body) {
        multiParts.add(new Multipart(name, body));
        return this;
    }


    public static final class Multipart implements Serializable {
        // body的key
        public String key;
        // body的内容
        public RequestBody requestBody;

        public Multipart(String name, RequestBody requestBody) {
            this.key = name;
            this.requestBody = requestBody;
        }
    }
}