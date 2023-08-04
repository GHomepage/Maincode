
package com.baidu.duer.dcs.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

public class NetWorkStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int netWorkState = NetWorkUtil.getNetWorkType(context);
            if (listener != null) {
                listener.onNetWorkStateChange(netWorkState);
            }
        }
    }


    private INetWorkStateListener listener;

    public interface INetWorkStateListener {
        void onNetWorkStateChange(int netType);
    }

    public void setOnNetWorkStateListener(INetWorkStateListener listener) {
        this.listener = listener;
    }
}