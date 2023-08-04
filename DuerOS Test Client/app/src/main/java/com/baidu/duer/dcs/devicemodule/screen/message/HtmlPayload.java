
package com.baidu.duer.dcs.devicemodule.screen.message;

import com.baidu.duer.dcs.framework.message.Payload;

import java.io.Serializable;

public class HtmlPayload extends Payload implements Serializable {
    private String url;
    //本页面对应的唯一token
    private String token;

    public HtmlPayload() {
    }

    public HtmlPayload(String url, String token) {
        this.url = url;
        this.token = token;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
