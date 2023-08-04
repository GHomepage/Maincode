
package com.baidu.duer.dcs.devicemodule.system.message;

import com.baidu.duer.dcs.framework.message.Payload;

public class SetEndPointPayload extends Payload {
    private String endpoint;

    public SetEndPointPayload() {

    }

    public SetEndPointPayload(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }
}