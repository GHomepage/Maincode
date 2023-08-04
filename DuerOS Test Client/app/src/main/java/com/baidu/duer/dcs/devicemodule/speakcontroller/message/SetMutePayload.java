
package com.baidu.duer.dcs.devicemodule.speakcontroller.message;

import com.baidu.duer.dcs.framework.message.Payload;

public class SetMutePayload extends Payload {
    private boolean mute; //静音设置：true为设置静音；false为取消静音

    public SetMutePayload() {
    }

    public SetMutePayload(boolean mute) {
        this.mute = mute;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }

    public boolean getMute() {
        return this.mute;
    }
}