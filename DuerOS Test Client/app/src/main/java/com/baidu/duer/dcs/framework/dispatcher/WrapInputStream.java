
package com.baidu.duer.dcs.framework.dispatcher;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;


public class WrapInputStream extends BufferedInputStream {
    private static final int BUFFER_SIZE = 8192;

    public WrapInputStream(@NonNull InputStream in) {
        this(in, BUFFER_SIZE);
    }

    public WrapInputStream(@NonNull InputStream in, int size) {
        super(in, size);
    }

    @Override
    public synchronized int read(byte[] b, int off, int len) throws IOException {
        long start = System.currentTimeMillis();
        int ret = super.read(b, off, len);
        return ret;
    }
}
