
package com.baidu.duer.dcs.devicemodule.alerts.message;

import com.baidu.duer.dcs.framework.message.Payload;

import java.util.List;

public class AlertsStatePayload extends Payload {
    public List<Alert> allAlerts;
    public List<Alert> activeAlerts;

    public AlertsStatePayload(List<Alert> all, List<Alert> active) {
        this.allAlerts = all;
        this.activeAlerts = active;
    }
}