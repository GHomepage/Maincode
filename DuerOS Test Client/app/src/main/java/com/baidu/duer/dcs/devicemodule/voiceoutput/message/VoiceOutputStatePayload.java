
package com.baidu.duer.dcs.devicemodule.voiceoutput.message;

import com.baidu.duer.dcs.framework.message.Payload;

public class VoiceOutputStatePayload extends Payload {
    public String token;
    public long offsetInMilliseconds;
    public String playerActivity;

    public VoiceOutputStatePayload(String token, long offsetInMilliseconds, String playerActivity) {
        this.token = token;
        this.offsetInMilliseconds = offsetInMilliseconds;
        this.playerActivity = playerActivity;
    }
}