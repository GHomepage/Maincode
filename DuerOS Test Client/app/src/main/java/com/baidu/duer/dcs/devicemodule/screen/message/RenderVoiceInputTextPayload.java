
package com.baidu.duer.dcs.devicemodule.screen.message;

import com.baidu.duer.dcs.framework.message.Payload;

import java.io.Serializable;

public class RenderVoiceInputTextPayload extends Payload implements Serializable {
    // 语音识别的结果
    public String text;
    public Type type;

    public enum Type {
    }

    public RenderVoiceInputTextPayload() {
    }

    public RenderVoiceInputTextPayload(String text, Type type) {
        this.text = text;
        this.type = type;
    }

    @Override
    public String toString() {
        return "RenderVoiceInputTextPayload{"
                + "text='" + text + '\''
                + ", type='" + type + '\''
                + '}';
    }
}