
package com.baidu.duer.dcs.systeminterface;

public interface IPlatformFactory {
    IHandler createHandler();
    IHandler getMainHandler();
    IAudioRecord getAudioRecord();
    IWakeUp getWakeUp();
    IAudioInput getVoiceInput();
    IMediaPlayer createMediaPlayer();
    IMediaPlayer createAudioTrackPlayer();
    IAlertsDataStore createAlertsDataStore();
    IWebView getWebView();
    void setWebView(IWebView webView);
    IPlaybackController getPlayback();
}
