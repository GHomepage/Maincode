
package com.baidu.duer.dcs.devicemodule.audioplayer.message;

import com.baidu.duer.dcs.framework.message.Payload;


public class PlaybackStatePayload extends Payload {
    public String token;
    public long offsetInMilliseconds;
    public String playerActivity;

    public PlaybackStatePayload(String token, long offsetInMilliseconds, String playerActivity) {
        this.token = token;
        this.offsetInMilliseconds = offsetInMilliseconds;
        this.playerActivity = playerActivity;
    }
}