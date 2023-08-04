
package com.baidu.duer.dcs.systeminterface;

import com.baidu.duer.dcs.devicemodule.alerts.message.Alert;

import java.util.List;

public interface IAlertsDataStore {

    void readFromDisk(ReadResultListener listener);

    void writeToDisk(List<Alert> alerts, WriteResultListener listener);

    interface ReadResultListener {
        void onSucceed(List<Alert> alerts);

        void onFailed(String errMsg);
    }


    interface WriteResultListener {
        void onSucceed();

        void onFailed(String errMsg);
    }
}