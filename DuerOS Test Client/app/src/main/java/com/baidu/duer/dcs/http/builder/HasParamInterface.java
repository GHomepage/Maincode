
package com.baidu.duer.dcs.http.builder;

import java.util.Map;


public interface HasParamInterface {

    OkHttpRequestBuilder params(Map<String, String> params);


    OkHttpRequestBuilder addParams(String key, String val);
}