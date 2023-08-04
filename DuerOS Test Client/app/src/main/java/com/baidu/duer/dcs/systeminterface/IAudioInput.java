
package com.baidu.duer.dcs.systeminterface;

import com.baidu.duer.dcs.framework.message.DcsStreamRequestBody;

public interface IAudioInput {
    /**
     * 处理开始录音的逻辑
     */
    void startRecord();

    /**
     * 处理停止录音的逻辑
     */
    void stopRecord();

    void registerAudioInputListener(IAudioInputListener audioInputListener);

    interface IAudioInputListener {
            void onStartRecord(DcsStreamRequestBody dcsStreamRequestBody);
        void onStopRecord();
    }
}