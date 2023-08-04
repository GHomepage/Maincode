
package com.baidu.duer.dcs.devicemodule.voiceoutput.message;

import com.baidu.duer.dcs.framework.message.AttachedContentPayload;
import com.baidu.duer.dcs.framework.message.Payload;
import com.baidu.duer.dcs.framework.DcsStream;

import org.codehaus.jackson.annotate.JsonIgnore;

public class SpeakPayload extends Payload implements AttachedContentPayload {
    public String url;
    public String format;
    public String token;

    @JsonIgnore
    public DcsStream dcsStream;

    // start with cid:播报音频的Content-ID
    public void setUrl(String url) {
        this.url = url.substring(4);
    }

    @Override
    public boolean requiresAttachedContent() {
        return !hasAttachedContent();
    }

    @Override
    public boolean hasAttachedContent() {
        return dcsStream != null;
    }

    @Override
    public String getAttachedContentId() {
        return url;
    }

    @Override
    public DcsStream getAttachedContent() {
        return dcsStream;
    }

    @Override
    public void setAttachedContent(String cid, DcsStream dcsStream) {
        if (getAttachedContentId().equals(cid)) {
            this.dcsStream = dcsStream;
        } else {
            throw new IllegalArgumentException(
                    "Tried to add the wrong audio content to a Speak directive. This cid: "
                            + getAttachedContentId() + " other cid: " + cid);
        }
    }
}