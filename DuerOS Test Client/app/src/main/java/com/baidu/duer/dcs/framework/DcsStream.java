
package com.baidu.duer.dcs.framework;

import java.util.concurrent.LinkedBlockingDeque;

public class DcsStream {
    public String token;
    public final LinkedBlockingDeque<byte[]> dataQueue = new LinkedBlockingDeque<>();
    public volatile boolean isFin;
    // 采样率
    public int sampleRate;
    // 声道数
    public int channels;

    public boolean isFinished() {
        return isFin && dataQueue.size() == 0;
    }
}