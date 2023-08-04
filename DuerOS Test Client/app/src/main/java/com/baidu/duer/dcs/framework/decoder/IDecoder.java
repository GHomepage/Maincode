
package com.baidu.duer.dcs.framework.decoder;

import java.io.InputStream;

public interface IDecoder {

    void decode(InputStream inputStream) throws Exception;

    void interruptDecode();

    void release();


    void addOnDecodeListener(IDecodeListener decodeListener);


    void removeOnDecodeListener(IDecodeListener decodeListener);


    interface IDecodeListener {

        void onDecodeInfo(int sampleRate, int channels);

        void onDecodePcm(byte[] pcmData);

        void onDecodeFinished();
    }
}