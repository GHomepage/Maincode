package com.baidu.duer.dcs.androidsystemimpl.wakeup;

import android.content.Context;
import android.os.Handler;

import com.baidu.duer.dcs.systeminterface.IWakeUp;
import com.baidu.duer.dcs.util.LogUtil;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class WakeUpImpl implements IWakeUp {
    private static final String TAG = WakeUpImpl.class.getSimpleName();
    // 初始化唤醒词成功
    private static final int WAKEUP_INIT_SUCCEED = 0;
    // 唤醒词
    private static final String WAKEUP_WORD = "小度小度";
    private static final String WAKEUP_FILENAME = "libbdEasrS1MergeNormal.so";
    private WakeUpNative wakeUpNative;
    // Decode消费线程
    private WakeUpDecodeThread wakeUpDecodeThread;
    // callback
    private List<IWakeUpListener> wakeUpListeners;
    private Handler handler = new Handler();
    private LinkedBlockingDeque<byte[]> linkedBlockingDeque;
    private int wakeUpInitialRet;
    private Context context;

    public WakeUpImpl(Context context, LinkedBlockingDeque<byte[]> linkedBlockingDeque) {
        this.linkedBlockingDeque = linkedBlockingDeque;
        this.context = context.getApplicationContext();
        this.wakeUpNative = new WakeUpNative();
        this.wakeUpListeners = Collections.synchronizedList(new LinkedList<IWakeUpListener>());
        this.initWakeUp();
    }

    private void initWakeUp() {
        String path = this.context.getApplicationInfo().nativeLibraryDir
                + File.separatorChar + WAKEUP_FILENAME;
        LogUtil.d(TAG, "wakeup path:" + path);
        LogUtil.d(TAG, "wakeup exists:" + new File(path).exists());
        wakeUpInitialRet = wakeUpNative.wakeUpInitial(WAKEUP_WORD, path, 0);
        LogUtil.d(TAG, "wakeUpInitialRet:" + wakeUpInitialRet);
    }

    @Override
    public void startWakeUp() {
        if (wakeUpDecodeThread != null && wakeUpDecodeThread.isStart()) {
            LogUtil.d(TAG, "wakeup wakeUpDecodeThread  is Started !");
            return;
        }
        // 2.开始唤醒
        if (wakeUpInitialRet == WAKEUP_INIT_SUCCEED) {
            wakeUp();
        } else {
            LogUtil.d(TAG, "wakeup wakeUpInitialRet failed, not startWakeUp ");
        }
    }

    @Override
    public void stopWakeUp() {
        if (wakeUpDecodeThread != null) {
            wakeUpDecodeThread.stopWakeUp();
        }
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void releaseWakeUp() {
        int ret = wakeUpNative.wakeUpFree();
        LogUtil.d(TAG, "wakeUpFree-ret:" + ret);
    }

    @Override
    public void addWakeUpListener(IWakeUpListener listener) {
        wakeUpListeners.add(listener);
    }

    private void wakeUp() {
        wakeUpDecodeThread = new WakeUpDecodeThread(linkedBlockingDeque, wakeUpNative, handler);
        wakeUpDecodeThread.setWakeUpListener(new WakeUpDecodeThread.IWakeUpListener() {
            @Override
            public void onWakeUpSucceed() {
                fireOnWakeUpSucceed();
            }
        });
        wakeUpDecodeThread.startWakeUp();
    }

    private void fireOnWakeUpSucceed() {
        for (IWakeUpListener listener : wakeUpListeners) {
            listener.onWakeUpSucceed();
        }
    }
}