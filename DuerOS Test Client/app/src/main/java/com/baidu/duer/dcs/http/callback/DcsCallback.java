
package com.baidu.duer.dcs.http.callback;

import com.baidu.dcs.okhttp3.Call;
import com.baidu.dcs.okhttp3.Request;
import com.baidu.dcs.okhttp3.Response;

public abstract class DcsCallback<T> {

    public void onBefore(Request request, int id) {
    }

    public void onAfter(int id) {
    }

    public static DcsCallback backDefaultCallBack = new DcsCallback() {

        @Override
        public Object parseNetworkResponse(Response response, int id) throws Exception {
            return null;
        }

        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(Object response, int id) {

        }
    };

    public boolean validateResponse(Response response, int id) {
        return response.isSuccessful();
    }

    public abstract T parseNetworkResponse(Response response, int id) throws Exception;


    public abstract void onError(Call call, Exception e, int id);


    public abstract void onResponse(T response, int id);
}