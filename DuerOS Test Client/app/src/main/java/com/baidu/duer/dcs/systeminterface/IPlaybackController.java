
package com.baidu.duer.dcs.systeminterface;

import com.baidu.duer.dcs.framework.IResponseListener;


public interface IPlaybackController {
    void play(IResponseListener responseListener);

    void pause(IResponseListener responseListener);

    void previous(IResponseListener responseListener);

    void next(IResponseListener responseListener);

    void registerPlaybackListener(IPlaybackListener listener);

    interface IPlaybackListener {
        void onPlay(IResponseListener responseListener);

        void onPause(IResponseListener responseListener);

        void onPrevious(IResponseListener responseListener);

        void onNext(IResponseListener responseListener);
    }
}
