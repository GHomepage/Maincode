
package com.baidu.duer.dcs.framework.decoder;

import android.util.Log;

import com.baidu.duer.dcs.util.LogUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.SampleBuffer;

public class JLayerDecoderImpl extends BaseDecoder {
    public JLayerDecoderImpl() {
        super();
        Log.d(TAG, "Decoder-JLayerDecoderImpl");
    }

    @Override
    public void decode(InputStream inputStream) throws Exception {
        Decoder decoder = new Decoder();
        Bitstream bitstream = new Bitstream(inputStream);
        Header header;
        isStopRead = false;
        isGetMp3InfoFinished = false;
        int count = 0;
        while (!isStopRead && (header = bitstream.readFrame()) != null) {
            isDecoding = true;
            long start = System.currentTimeMillis();
            SampleBuffer sampleBuffer = (SampleBuffer) decoder.decodeFrame(header, bitstream);
            // 获取采样率等
            if (!isGetMp3InfoFinished) {
                fireOnDecodeInfo(sampleBuffer.getSampleFrequency(), sampleBuffer.getChannelCount());
                isGetMp3InfoFinished = true;
            }
            short[] buffer = sampleBuffer.getBuffer();
            byte[] pcm = new byte[buffer.length / 2];
            for (int i = 0; i < buffer.length / 2 / 2; i++) {
                int j = i * 2;
                pcm[j] = (byte) (buffer[i] & 0xff);
                pcm[j + 1] = (byte) ((buffer[i] >> 8) & 0xff);
            }
            if (count == 0 || count == 1) {
                byte[] newPcm = avoidNullPcm(pcm);
                if (newPcm != null) {
                    fireOnDecodePcm(newPcm);
                }
            } else {
                fireOnDecodePcm(pcm);
            }
            count++;
            bitstream.closeFrame();
            long end = System.currentTimeMillis();
            Log.i(TAG, "after decode pcm.length:" + pcm.length + "," + (end - start));
        }
        isDecoding = false;
        fireOnDecodeFinished();
        inputStream.close();
    }

    @Override
    protected void read(byte[] mp3Data) {

    }
}