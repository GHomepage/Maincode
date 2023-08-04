
package com.baidu.duer.dcs.devicemodule.audioplayer.message;

import com.baidu.duer.dcs.framework.message.Payload;

public class ClearQueuePayload extends Payload {
    public enum ClearBehavior {
        CLEAR_ENQUEUED,
        CLEAR_ALL
    }

    public ClearBehavior clearBehavior;
}