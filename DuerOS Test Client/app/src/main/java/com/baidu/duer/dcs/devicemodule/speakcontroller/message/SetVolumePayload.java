
package com.baidu.duer.dcs.devicemodule.speakcontroller.message;

import com.baidu.duer.dcs.framework.message.Payload;


public class SetVolumePayload extends Payload {
    private long volume;

    public SetVolumePayload() {
    }

    public SetVolumePayload(long volume) {
        this.volume = volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public long getVolume() {
        return volume;
    }
}