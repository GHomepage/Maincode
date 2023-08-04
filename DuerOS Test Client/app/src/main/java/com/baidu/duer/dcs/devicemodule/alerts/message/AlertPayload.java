
package com.baidu.duer.dcs.devicemodule.alerts.message;

import com.baidu.duer.dcs.framework.message.Payload;

public class AlertPayload extends Payload {
    public String token;

    public AlertPayload(String token) {
        this.token = token;
    }
}