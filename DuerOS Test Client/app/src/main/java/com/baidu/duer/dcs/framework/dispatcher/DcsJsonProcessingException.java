
package com.baidu.duer.dcs.framework.dispatcher;

import org.codehaus.jackson.JsonProcessingException;

public class DcsJsonProcessingException extends JsonProcessingException {
    private String unparsedCotent;

    public DcsJsonProcessingException(String message, JsonProcessingException exception, String unparsedCotent) {
        super(message, exception);
        this.unparsedCotent = unparsedCotent;
    }

    public String getUnparsedCotent() {
        return unparsedCotent;
    }
}