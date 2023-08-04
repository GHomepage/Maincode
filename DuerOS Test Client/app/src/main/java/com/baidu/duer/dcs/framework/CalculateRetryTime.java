
package com.baidu.duer.dcs.framework;


public class CalculateRetryTime {

    // 重连DCS时间间隔（ms），同时进行随机分布，防止dos攻击
    private static final int[] RETRY_TIME = {250, 1000, 3000, 5000, 10000, 20000, 30000, 60000};
    static final double RETRY_RANDOMIZATION_FACTOR = 0.5;
    static final double RETRY_DECREASE_FACTOR = 1 / (RETRY_RANDOMIZATION_FACTOR + 1);
    static final double RETRY_INCREASE_FACTOR = (RETRY_RANDOMIZATION_FACTOR + 1);
    private int retryCount = 0;

    public CalculateRetryTime() {

    }

    public void reset() {
        retryCount = 0;
    }

    public int getRetryTime() {
        final int length = RETRY_TIME.length;

        if (retryCount >= length) {
            retryCount = length - 1;
        }

        int retryTime = RETRY_TIME[retryCount];
        ++retryCount;
        double min = retryTime * RETRY_DECREASE_FACTOR;
        double max = retryTime * RETRY_INCREASE_FACTOR;

        int delayTime = (int) (min + (Math.random() * (max - min)));
        return delayTime;
    }
}