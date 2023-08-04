
package com.baidu.duer.dcs.http.intercepter;

import com.baidu.dcs.okhttp3.Interceptor;
import com.baidu.dcs.okhttp3.Request;
import com.baidu.dcs.okhttp3.Response;

import java.io.IOException;


public class RetryInterceptor implements Interceptor {
    // 最大重试次数，假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）
    private int maxRetry;
    private int retryNum = 0;

    public RetryInterceptor(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        while (!response.isSuccessful() && retryNum < maxRetry) {
            retryNum++;
            response = chain.proceed(request);
        }
        return response;
    }
}