
package com.baidu.duer.dcs.devicemodule.voiceoutput.message;

import com.baidu.duer.dcs.framework.message.Payload;

import org.codehaus.jackson.map.annotate.JsonSerialize;


@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SpeechLifecyclePayload extends Payload {
    private String token; //该播报对应的token，对应Speak指令中的token

    public SpeechLifecyclePayload(String token) {
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}