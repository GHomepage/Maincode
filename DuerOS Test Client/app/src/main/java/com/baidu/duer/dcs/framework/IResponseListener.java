
package com.baidu.duer.dcs.framework;

public interface IResponseListener {

    void onSucceed(int statusCode);


    void onFailed(String errorMessage);
}
