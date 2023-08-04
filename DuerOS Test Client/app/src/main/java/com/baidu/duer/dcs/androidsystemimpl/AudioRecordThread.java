package com.baidu.duer.dcs.androidsystemimpl;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import com.baidu.duer.dcs.systeminterface.IAudioRecord;
import com.baidu.duer.dcs.util.LogUtil;

import java.util.concurrent.LinkedBlockingDeque;

public class AudioRecordThread extends Thread implements IAudioRecord {
    private static final String TAG = "AudioRecordThread";
    private static final int SAMPLE_RATE_HZ = 16000;
    private int bufferSize;
    private AudioRecord audioRecord;
    private volatile boolean isStartRecord = false;
    private LinkedBlockingDeque<byte[]> linkedBlockingDeque;

    public AudioRecordThread(LinkedBlockingDeque<byte[]> linkedBlockingDeque) {
        this.linkedBlockingDeque = linkedBlockingDeque;
        bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE_HZ, AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE_HZ, AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT, bufferSize);
    }

    @Override
    public void startRecord() {
        if (isStartRecord) {
            return;
        }
        isStartRecord = true;
        this.start();
    }

    @Override
    public void stopRecord() {
        isStartRecord = false;
    }

    @Override
    public void run() {
        super.run();
        LogUtil.d(TAG, "audioRecord startRecording ");
        audioRecord.startRecording();
        byte[] buffer = new byte[bufferSize];
        while (isStartRecord) {
            int readBytes = audioRecord.read(buffer, 0, bufferSize);
            if (readBytes > 0) {
                linkedBlockingDeque.add(buffer);
            }
        }
        linkedBlockingDeque.clear();
        audioRecord.stop();
        audioRecord.release();
        audioRecord = null;
        LogUtil.d(TAG, "audioRecord release ");
    }
}