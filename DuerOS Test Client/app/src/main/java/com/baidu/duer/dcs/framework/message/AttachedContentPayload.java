
package com.baidu.duer.dcs.framework.message;

import com.baidu.duer.dcs.framework.DcsStream;


public interface AttachedContentPayload {
    boolean requiresAttachedContent();
    boolean hasAttachedContent();
    String getAttachedContentId();
    DcsStream getAttachedContent();
    void setAttachedContent(String contentId, DcsStream dcsStream);
}
