
package com.baidu.duer.dcs.oauth.api;

import android.os.Bundle;

import com.baidu.duer.dcs.util.CommonUtil;

import java.net.MalformedURLException;
import java.net.URL;

public class OauthNetUtil {

    public static Bundle parseUrl(String url) {
        Bundle ret;
        url = url.replace("bdconnect", "http");
        try {
            URL urlParam = new URL(url);
            ret = CommonUtil.decodeUrl(urlParam.getQuery());
            ret.putAll(CommonUtil.decodeUrl(urlParam.getRef()));
            return ret;
        } catch (MalformedURLException e) {
            return new Bundle();
        }
    }
}