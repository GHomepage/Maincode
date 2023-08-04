
package com.baidu.duer.dcs.devicemodule.voiceinput.message;

import com.baidu.duer.dcs.framework.message.Payload;

public class ListenStartedPayload extends Payload {
    public static final String FORMAT = "AUDIO_L16_RATE_16000_CHANNELS_1";
    private String format;

    public ListenStartedPayload(String format) {
        this.format = format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}