
package com.baidu.duer.dcs.devicemodule.alerts.message;

import com.baidu.duer.dcs.framework.message.Payload;

import java.io.Serializable;

public class DeleteAlertPayload extends Payload implements Serializable {
    public String token;

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
