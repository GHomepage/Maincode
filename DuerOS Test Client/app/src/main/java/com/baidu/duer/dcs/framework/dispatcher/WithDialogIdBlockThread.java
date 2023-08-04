package com.baidu.duer.dcs.framework.dispatcher;

import com.baidu.duer.dcs.devicemodule.voiceoutput.ApiConstants;
import com.baidu.duer.dcs.framework.DcsResponseDispatcher;
import com.baidu.duer.dcs.framework.message.DcsResponseBody;

import java.util.concurrent.BlockingQueue;


public class WithDialogIdBlockThread extends BaseBlockResponseThread {

    public WithDialogIdBlockThread(BlockingQueue<DcsResponseBody> responseBodyDeque,
                                   DcsResponseDispatcher.IDcsResponseHandler responseHandler,
                                   String threadName) {
        super(responseBodyDeque, responseHandler, threadName);
    }

    @Override
    boolean shouldBlock(DcsResponseBody responseBody) {
        String directiveName = responseBody.getDirective().getName();
        return directiveName != null && directiveName.length() > 0
                && directiveName.equals(ApiConstants.Directives.Speak.NAME);
    }
}
