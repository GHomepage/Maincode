
package com.baidu.duer.dcs.framework.dispatcher;

import com.baidu.duer.dcs.framework.DcsStream;


public class AudioData {
    public String contentId;
    public DcsStream dcsStream;

    public AudioData(String contentId, DcsStream dcsStream) {
        this.contentId = contentId;
        this.dcsStream = dcsStream;
    }
}