
package com.baidu.duer.dcs.systeminterface;

public interface IWakeUp {

    void startWakeUp();


    void stopWakeUp();

    void releaseWakeUp();


    void addWakeUpListener(IWakeUpListener listener);


    interface IWakeUpListener {

        void onWakeUpSucceed();
    }
}