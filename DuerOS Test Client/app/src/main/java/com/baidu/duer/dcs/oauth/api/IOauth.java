
package com.baidu.duer.dcs.oauth.api;

public interface IOauth {

    String getAccessToken();

    void authorize();

    boolean isSessionValid();
}