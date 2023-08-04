
package com.baidu.duer.dcs.http.request;

import com.baidu.dcs.okhttp3.FormBody;
import com.baidu.dcs.okhttp3.Headers;
import com.baidu.dcs.okhttp3.MultipartBody;
import com.baidu.dcs.okhttp3.Request;
import com.baidu.dcs.okhttp3.RequestBody;
import com.baidu.duer.dcs.http.builder.PostMultipartBuilder;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class PostMultipartRequest extends OkHttpRequest {
    private List<PostMultipartBuilder.Multipart> multiParts;

    public PostMultipartRequest(String url,
                                Object tag,
                                Map<String, String> params,
                                Map<String, String> headers,
                                LinkedList<PostMultipartBuilder.Multipart> multiParts,
                                int id) {
        super(url, tag, params, headers, id);
        this.multiParts = multiParts;
    }

    @Override
    protected RequestBody buildRequestBody() {
        if (multiParts == null || multiParts.isEmpty()) {
            FormBody.Builder builder = new FormBody.Builder();
            addParams(builder);
            return builder.build();
        } else {
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            addParams(builder);
            for (int i = 0; i < multiParts.size(); i++) {
                PostMultipartBuilder.Multipart part = multiParts.get(i);
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + part.key + "\""),
                        part.requestBody);
            }
            return builder.build();
        }
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return builder.post(requestBody).build();
    }

    private void addParams(FormBody.Builder builder) {
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
    }

    private void addParams(MultipartBody.Builder builder) {
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
                        RequestBody.create(null, params.get(key)));
            }
        }
    }
}