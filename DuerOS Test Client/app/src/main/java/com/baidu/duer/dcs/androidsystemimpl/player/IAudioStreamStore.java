package com.baidu.duer.dcs.androidsystemimpl.player;

import java.io.InputStream;

public interface IAudioStreamStore {

    void save(InputStream inputStream);

    void cancel();

    void speakAfter();

    void setOnStoreListener(OnStoreListener listener);

    interface OnStoreListener {
        void onStart();

        void onComplete(String path);

        void onError(String errorMessage);
    }

    class SimpleOnStoreListener implements OnStoreListener {
        @Override
        public void onStart() {
        }

        @Override
        public void onComplete(String path) {
        }

        @Override
        public void onError(String errorMessage) {
        }
    }
}
