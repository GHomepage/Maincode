
package com.baidu.duer.dcs.framework.message;

public class BaseMessage {
    private Header header;
    private Payload payload;

    public BaseMessage(Header header, Payload payload) {
        this.header = header;
        this.payload = payload;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Header getHeader() {
        return header;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public Payload getPayload() {
        return payload;
    }
}