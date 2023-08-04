package com.baidu.duer.dcs.androidsystemimpl.audioinput;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;

import com.baidu.duer.dcs.util.LogUtil;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.LinkedBlockingDeque;

import okio.BufferedSink;

public class AudioVoiceInputThread extends Thread {
    private static final String TAG ="AudioVoiceInputThread";
    private volatile boolean isStart = false;
    private BufferedSink bufferedSink;
    // 音频数据
    private LinkedBlockingDeque<byte[]> linkedBlockingDeque;
    private Handler handler;
    public AudioVoiceInputThread(LinkedBlockingDeque<byte[]> linkedBlockingDeque,
                                 BufferedSink bufferedSink,
                                 Handler handler) {
        this.linkedBlockingDeque = linkedBlockingDeque;
        this.bufferedSink = bufferedSink;
        this.handler = handler;
    }


    public void startWriteStream() {
        if (isStart) {
            return;
        }
        isStart = true;
        this.start();
    }


    public void stopWriteStream() {
        isStart = false;
    }

    private boolean isfirst = true;

    @Override
    public void run() {
        super.run();
        while (isStart) {
            try {
                byte[] recordAudioData = linkedBlockingDeque.pollFirst();
                if (null != recordAudioData) {
                    if (isfirst) {
                        isfirst = false;
                    }
                    bufferedSink.write(recordAudioData);
                }
            } catch (IOException e) {
                e.printStackTrace();
                LogUtil.d(TAG, "writeTo IOException", e);
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.d(TAG, "writeTo Exception", e);
            }
        }
        if (linkedBlockingDeque.size() > 0) {
            byte[] recordAudioData = linkedBlockingDeque.pollFirst();
            if (null != recordAudioData) {
                LogUtil.d(TAG, "finally writeTo size:" + recordAudioData.length);
                try {
                    bufferedSink.write(recordAudioData);
                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtil.d(TAG, " >0 writeTo IOException", e);
                }
            }
        }
        try {
            bufferedSink.flush();
            bufferedSink.close();
            LogUtil.d(TAG, "closed");
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.d(TAG, "IOException ", e);
        }
        LogUtil.d(TAG, "onWriteFinished ");
        // 写入完成
        if (listener != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onWriteFinished();
                }
            });
        }
    }

    private IAudioInputListener listener;

    public void setAudioInputListener(IAudioInputListener listener) {
        this.listener = listener;
    }

    public interface IAudioInputListener {

        void onWriteFinished();
    }
}