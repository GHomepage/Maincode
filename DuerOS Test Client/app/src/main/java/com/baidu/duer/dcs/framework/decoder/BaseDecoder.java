
package com.baidu.duer.dcs.framework.decoder;

import android.util.Log;

import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class BaseDecoder implements IDecoder {
    protected static final String TAG = "BaseDecoder";
    // 读取缓存
    private static final int BUFFER_SIZE = 8 * 1024;
    // 是否正在解码
    volatile boolean isDecoding = false;
    // 是否停止读取
    volatile boolean isStopRead = false;
    // 回调
    private final List<IDecodeListener> decodeListeners;
    // 是否获取到了mp3的信息
    volatile boolean isGetMp3InfoFinished;

    BaseDecoder() {
        this.decodeListeners = Collections.synchronizedList(
                new LinkedList<IDecodeListener>());
    }

    @Override
    public void decode(InputStream inputStream) throws Exception {
        byte[] buffer = new byte[BUFFER_SIZE];
        int readBytes;
        isStopRead = false;
        while (!isStopRead && (readBytes = inputStream.read(buffer, 0, buffer.length)) != -1) {
            Log.d(TAG, "read one tts data readBytes:" + readBytes + "," + System.currentTimeMillis());
            isDecoding = true;
            byte[] temp = new byte[readBytes];
            System.arraycopy(buffer, 0, temp, 0, readBytes);
            read(temp);
        }
        isDecoding = false;
        Log.d(TAG, "decoder finished.");
        fireOnDecodeFinished();
        inputStream.close();
    }

    @Override
    public void interruptDecode() {
        if (isDecoding) {
            isStopRead = true;
        }
    }

    @Override
    public void release() {
        isStopRead = true;
        isDecoding = false;
        decodeListeners.clear();
    }

    @Override
    public void addOnDecodeListener(IDecodeListener decodeListener) {
        synchronized (decodeListeners) {
            if (!decodeListeners.contains(decodeListener)) {
                this.decodeListeners.add(decodeListener);
            }
        }
    }

    @Override
    public void removeOnDecodeListener(IDecodeListener decodeListener) {
        synchronized (decodeListeners) {
            if (decodeListeners.contains(decodeListener)) {
                decodeListeners.remove(decodeListener);
            }
        }
    }


    protected byte[] avoidNullPcm(byte[] pcm) {
        // Log.i(TAG, "Decoder:" + ArrayUtils.toString(pcm));
        int index = -1;
        for (int j = 0; j < pcm.length; j++) {
            if (pcm[j] != 0) {
                index = j;
                break;
            }
        }
        Log.d(TAG, "index=" + index);
        if (index != -1) {
            byte[] buffer = new byte[pcm.length - index];
            System.arraycopy(pcm, index, buffer, 0, buffer.length);
            return buffer;
        }
        return null;
    }

    void fireOnDecodeFinished() {
        synchronized (decodeListeners) {
            for (IDecoder.IDecodeListener listener : decodeListeners) {
                listener.onDecodeFinished();
            }
        }
    }

    void fireOnDecodePcm(byte[] pcm) {
        synchronized (decodeListeners) {
            for (IDecoder.IDecodeListener listener : decodeListeners) {
                listener.onDecodePcm(pcm);
            }
        }
    }

    void fireOnDecodeInfo(int sampleRate, int channels) {
        synchronized (decodeListeners) {
            for (IDecoder.IDecodeListener listener : decodeListeners) {
                listener.onDecodeInfo(sampleRate, channels);
            }
        }
    }

    protected abstract void read(byte[] mp3Data);
}