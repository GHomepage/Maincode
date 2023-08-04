package com.baidu.duer.dcs.devicemodule.system.message;

import com.baidu.duer.dcs.devicemodule.system.HandleDirectiveException.ExceptionType;
import com.baidu.duer.dcs.framework.message.Payload;


public class ExceptionEncounteredPayload extends Payload {
    private String unparsedDirective;
    private Error error;

    public ExceptionEncounteredPayload(String unparsedDirective, ExceptionType type, String message) {
        this.unparsedDirective = unparsedDirective;
        Error error = new Error(type, message);
        this.error = error;
    }

    public void setUnparsedDirective(String unparsedDirective) {
        this.unparsedDirective = unparsedDirective;
    }

    public String getUnparsedDirective() {
        return unparsedDirective;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public Error getError() {
        return error;
    }

    public static class Error {
        public ExceptionType type;
        public String message;

        public Error(ExceptionType type, String message) {
            this.type = type;
            this.message = message;
        }
    }
}