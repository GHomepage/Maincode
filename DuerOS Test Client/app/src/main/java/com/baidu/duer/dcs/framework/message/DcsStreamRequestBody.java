
package com.baidu.duer.dcs.framework.message;

import com.baidu.dcs.okhttp3.MediaType;
import com.baidu.dcs.okhttp3.RequestBody;
import com.baidu.duer.dcs.http.OkHttpMediaType;

import java.io.IOException;

import okio.BufferedSink;
import okio.Okio;
import okio.Pipe;


public class DcsStreamRequestBody extends RequestBody {
    private final Pipe pipe = new Pipe(8192);
    private final BufferedSink mSink = Okio.buffer(pipe.sink());

    public BufferedSink sink() {
        return mSink;
    }

    @Override
    public MediaType contentType() {
        return OkHttpMediaType.MEDIA_STREAM_TYPE;
    }

    @Override
    public void writeTo(BufferedSink bufferedSink) throws IOException {
        bufferedSink.writeAll(pipe.source());
    }
}
