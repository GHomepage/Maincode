
package com.baidu.duer.dcs.devicemodule.voiceinput.message;

import com.baidu.duer.dcs.framework.message.Payload;


public class ListenPayload extends Payload {
    private long timeoutInMilliseconds;

    public void setTimeoutInMilliseconds(long timeoutInMilliseconds) {
        this.timeoutInMilliseconds = timeoutInMilliseconds;
    }

    public long getTimeoutInMilliseconds() {
        return timeoutInMilliseconds;
    }
}