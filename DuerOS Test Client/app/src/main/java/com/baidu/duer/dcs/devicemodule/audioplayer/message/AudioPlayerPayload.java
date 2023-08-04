
package com.baidu.duer.dcs.devicemodule.audioplayer.message;

import com.baidu.duer.dcs.framework.message.Payload;

public class AudioPlayerPayload extends Payload {
    public String token;
    public long offsetInMilliseconds;

    public AudioPlayerPayload(String token, long offsetInMilliseconds) {
        this.token = token;
        this.offsetInMilliseconds = offsetInMilliseconds;
    }
}