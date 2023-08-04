
package com.baidu.duer.dcs.wakeup;

import com.baidu.duer.dcs.systeminterface.IAudioRecord;
import com.baidu.duer.dcs.systeminterface.IWakeUp;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class WakeUp {
    private IWakeUp iWakeUp;
    private List<IWakeUp.IWakeUpListener> wakeUpListeners;
    private IAudioRecord iAudioRecord;

    public WakeUp(IWakeUp iWakeUp, IAudioRecord iAudioRecord) {
        this.iWakeUp = iWakeUp;
        this.iAudioRecord = iAudioRecord;
        this.wakeUpListeners = Collections.synchronizedList(new LinkedList<IWakeUp.IWakeUpListener>());
        this.iWakeUp.addWakeUpListener(new IWakeUp.IWakeUpListener() {
            @Override
            public void onWakeUpSucceed() {
                fireOnWakeUpSucceed();
            }
        });
        // 启动音频采集
        this.iAudioRecord.startRecord();
    }

    private void fireOnWakeUpSucceed() {
        for (IWakeUp.IWakeUpListener listener : wakeUpListeners) {
            listener.onWakeUpSucceed();
        }
    }

    public void startWakeUp() {
        iWakeUp.startWakeUp();
    }

    public void stopWakeUp() {
        iWakeUp.stopWakeUp();
    }


    public void releaseWakeUp() {
        iAudioRecord.stopRecord();
        iWakeUp.releaseWakeUp();
    }


    public void addWakeUpListener(IWakeUp.IWakeUpListener listener) {
        wakeUpListeners.add(listener);
    }

    public void removeWakeUpListener(IWakeUp.IWakeUpListener listener) {
        if (wakeUpListeners.contains(listener)) {
            wakeUpListeners.remove(listener);
        }
    }
}