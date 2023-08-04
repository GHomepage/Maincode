
package com.baidu.duer.dcs.http;

import com.baidu.duer.dcs.framework.message.DcsRequestBody;
import com.baidu.duer.dcs.framework.message.DcsStreamRequestBody;
import com.baidu.duer.dcs.http.callback.DcsCallback;

public interface HttpRequestInterface {

    void doPostEventStringAsync(DcsRequestBody requestBody, DcsCallback dcsCallback);


    void doPostEventMultipartAsync(DcsRequestBody requestBody,
                                   DcsStreamRequestBody streamRequestBody,
                                   DcsCallback dcsCallback);


    void doGetDirectivesAsync(DcsCallback dcsCallback);


    void doGetPingAsync(DcsRequestBody requestBody, DcsCallback dcsCallback);


    void cancelRequest(Object requestTag);
}