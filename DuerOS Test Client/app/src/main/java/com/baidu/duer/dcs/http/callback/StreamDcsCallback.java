
package com.baidu.duer.dcs.http.callback;

import com.baidu.dcs.okhttp3.Response;

import java.io.InputStream;


public abstract class StreamDcsCallback extends DcsCallback<InputStream> {
    @Override
    public InputStream parseNetworkResponse(Response response, int id) throws Exception {
        return response.body().byteStream();
    }
}