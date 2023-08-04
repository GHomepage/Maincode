
package com.baidu.duer.dcs.devicemodule.system.message;

import com.baidu.duer.dcs.framework.message.Payload;

public class UserInactivityReportPayload extends Payload {
    private long inactiveTimeInSeconds;

    public UserInactivityReportPayload(long inactiveTimeInSeconds) {
        this.inactiveTimeInSeconds = inactiveTimeInSeconds;
    }

    void setInactiveTimeInSeconds(long inactiveTimeInSeconds) {
        this.inactiveTimeInSeconds = inactiveTimeInSeconds;
    }

    long getInactiveTimeInSeconds() {
        return inactiveTimeInSeconds;
    }
}