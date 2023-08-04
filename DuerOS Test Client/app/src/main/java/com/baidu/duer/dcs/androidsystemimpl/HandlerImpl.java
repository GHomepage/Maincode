package com.baidu.duer.dcs.androidsystemimpl;

import android.os.Handler;
import android.os.Looper;

import com.baidu.duer.dcs.systeminterface.IHandler;

public class HandlerImpl implements IHandler {
    private final Handler handler;

    public HandlerImpl() {
        handler = new Handler();
    }

    public HandlerImpl(Looper looper) {
        handler = new Handler(looper);
    }

    @Override
    public boolean post(Runnable runnable) {
        return handler.post(runnable);
    }
}
