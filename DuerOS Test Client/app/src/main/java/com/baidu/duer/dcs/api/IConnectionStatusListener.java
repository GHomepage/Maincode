package com.baidu.duer.dcs.api;

public interface IConnectionStatusListener {

    void onConnectStatus(ConnectionStatus connectionStatus);

    enum ConnectionStatus {
        DISCONNECTED,
        PENDING,
        CONNECTED
    }
}
