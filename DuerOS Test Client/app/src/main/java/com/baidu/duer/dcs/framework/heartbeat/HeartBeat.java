
package com.baidu.duer.dcs.framework.heartbeat;

import com.baidu.dcs.okhttp3.Call;
import com.baidu.dcs.okhttp3.Response;
import com.baidu.duer.dcs.http.HttpConfig;
import com.baidu.duer.dcs.http.HttpRequestInterface;
import com.baidu.duer.dcs.http.callback.ResponseCallback;

import java.util.Timer;
import java.util.TimerTask;

public class HeartBeat {
    private static final String TAG = HeartBeat.class.getSimpleName();

    private static final long PING_TIME_SUCCEED = 270 * 1000;

    // 设置ping失败后的(ping失败即代表directive请求失败了)请求的时间间隔
    private static final long PING_TIME_FAILED = 5 * 1000;
    private final HttpRequestInterface httpRequest;
    private Timer timer;
    private PingTask pingTask;

    public HeartBeat(HttpRequestInterface httpRequest) {
        this.httpRequest = httpRequest;
        timer = new Timer();
    }

    public void release() {
        stop();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void start() {
        startPing(PING_TIME_SUCCEED, PING_TIME_SUCCEED);
    }

    public void stop() {
        if (pingTask != null) {
            pingTask.cancel();
        }
    }

    private void startPing(long delay, long timeInterval) {
        if (pingTask != null) {
            pingTask.cancel();
        }
        pingTask = new PingTask();
        if (timer != null) {
            timer.schedule(pingTask, delay, timeInterval);
        }
    }

    private void startPing() {
        httpRequest.cancelRequest(HttpConfig.HTTP_PING_TAG);
        httpRequest.doGetPingAsync(null, new ResponseCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public Response parseNetworkResponse(Response response, int id) throws Exception {
                return super.parseNetworkResponse(response, id);
            }
        });
    }

    private final class PingTask extends TimerTask {
        @Override
        public void run() {
            startPing();
        }
    }
}
