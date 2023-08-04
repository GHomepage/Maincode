
package com.baidu.duer.dcs.http.callback;


import com.baidu.dcs.okhttp3.Response;

public abstract class StringDcsCallback extends DcsCallback<String> {
    @Override
    public String parseNetworkResponse(Response response, int id) throws Exception {
        return response.body().string();
    }
}