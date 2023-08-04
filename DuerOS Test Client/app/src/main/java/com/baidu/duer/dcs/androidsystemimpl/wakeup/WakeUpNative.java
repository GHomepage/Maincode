
package com.baidu.duer.dcs.androidsystemimpl.wakeup;

public class WakeUpNative {

    static {
        System.loadLibrary("wakeup");
        System.loadLibrary("bdEASRAndroid");
    }


    public native int wakeUpInitial(String wakeUpWd, String sFile, int mode);

    public native int wakeUpReset();

    public native int wakeUpDecode(short[] data,
                                   int dataLen,
                                   String senArr,
                                   int expectNum,
                                   int wakeWord_frame_len,
                                   boolean is_confidence,
                                   int voice_offset,
                                   boolean bEd
    );


    public native int wakeUpFree();
}