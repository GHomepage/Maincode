
package com.baidu.duer.dcs.http.callback;

import com.baidu.dcs.okhttp3.Call;
import com.baidu.dcs.okhttp3.Response;
import com.baidu.duer.dcs.util.LogUtil;

public class ResponseCallback extends DcsCallback<Response> {
    private static final String TAG = "ResponseCallback";

    @Override
    public Response parseNetworkResponse(Response response, int id) throws Exception {
        return response;
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        LogUtil.d(TAG, "onError:" + e.getMessage());
    }

    @Override
    public void onResponse(Response response, int id) {
        LogUtil.d(TAG, "onResponse:" + response.code());
    }
}