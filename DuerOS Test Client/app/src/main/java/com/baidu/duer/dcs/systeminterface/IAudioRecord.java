
package com.baidu.duer.dcs.systeminterface;


public interface IAudioRecord {
    /**
     * 开始录音，采集音频数据
     */
    void startRecord();

    /**
     * 停止录音
     */
    void stopRecord();
}