
package com.baidu.duer.dcs.devicemodule.audioplayer.message;


public class PlaybackStutterFinishedPayload extends AudioPlayerPayload {
    public long stutterDurationInMilliseconds;

    public PlaybackStutterFinishedPayload(String token, long offsetInMilliseconds,
                                          long stutterDurationInMilliseconds) {
        super(token, offsetInMilliseconds);
        this.stutterDurationInMilliseconds = stutterDurationInMilliseconds;
    }
}