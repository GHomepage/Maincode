
package com.baidu.duer.dcs.devicemodule.alerts.message;

import com.baidu.duer.dcs.framework.message.Payload;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;


public class SetAlertPayload extends Payload implements Serializable {
    public enum AlertType {
        ALARM,
        TIMER
    }

    // alert唯一token
    private String token;
    private AlertType type;
    private String scheduledTime;
    private String content;

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setType(String type) {
        this.type = AlertType.valueOf(type.toUpperCase());
    }

    public AlertType getType() {
        return type;
    }

    @JsonProperty("scheduledTime")
    public void setScheduledTime(String dateTime) {
        scheduledTime = dateTime;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SetAlertPayload{"
                + "token='"
                + token
                + '\''
                + ", type="
                + type
                + ", scheduledTime='"
                + scheduledTime
                + '\''
                + '}';
    }
}
