
package com.baidu.duer.dcs.devicemodule.audioplayer.message;

import com.baidu.duer.dcs.framework.message.Payload;
import com.baidu.duer.dcs.systeminterface.IMediaPlayer;


public class PlaybackFailedPayload extends Payload {
    private String token;
    private PlaybackStatePayload currentPlaybackState;
    private ErrorStructure error;

    public PlaybackFailedPayload(String token, PlaybackStatePayload playbackState,
                                 IMediaPlayer.ErrorType errorType) {
        this.token = token;
        this.currentPlaybackState = playbackState;
        error = new ErrorStructure(errorType);
    }

    public String getToken() {
        return token;
    }

    public PlaybackStatePayload getCurrentPlaybackState() {
        return currentPlaybackState;
    }

    public ErrorStructure getError() {
        return error;
    }

    private static final class ErrorStructure {
        private IMediaPlayer.ErrorType type;
        private String message;

        public ErrorStructure(IMediaPlayer.ErrorType type) {
            this.type = type;
            this.message = type.getMessage();
        }

        public IMediaPlayer.ErrorType getType() {
            return type;
        }

        public String getMessage() {
            return message;
        }
    }
}