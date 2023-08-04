
package com.baidu.duer.dcs.androidsystemimpl.playbackcontroller;

import com.baidu.duer.dcs.framework.IResponseListener;
import com.baidu.duer.dcs.systeminterface.IPlaybackController;


public class IPlaybackControllerImpl implements IPlaybackController {
    private IPlaybackListener playbackListener;

    @Override
    public void play(IResponseListener responseListener) {
        if (playbackListener == null) {
            return;
        }
        playbackListener.onPlay(responseListener);
    }

    @Override
    public void pause(IResponseListener responseListener) {
        if (playbackListener == null) {
            return;
        }
        playbackListener.onPause(responseListener);
    }

    @Override
    public void previous(IResponseListener responseListener) {
        if (playbackListener == null) {
            return;
        }
        playbackListener.onPrevious(responseListener);
    }

    @Override
    public void next(IResponseListener responseListener) {
        if (playbackListener == null) {
            return;
        }
        playbackListener.onNext(responseListener);
    }

    @Override
    public void registerPlaybackListener(IPlaybackListener listener) {
        this.playbackListener = listener;
    }
}
