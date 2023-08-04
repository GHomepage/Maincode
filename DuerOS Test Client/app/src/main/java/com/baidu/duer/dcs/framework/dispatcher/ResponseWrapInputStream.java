
package com.baidu.duer.dcs.framework.dispatcher;

import android.util.Log;

import com.baidu.duer.dcs.util.FileUtil;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ResponseWrapInputStream extends BufferedInputStream {
    private static final int BUFFER_SIZE = 8192;

    public ResponseWrapInputStream(InputStream in) {
        super(in, BUFFER_SIZE);
    }

    @Override
    public synchronized int read(byte[] b, int off, int len) throws IOException {
        int ret = super.read(b, off, len);
        return ret;
    }
}