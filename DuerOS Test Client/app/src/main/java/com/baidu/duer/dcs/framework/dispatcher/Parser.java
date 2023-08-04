
package com.baidu.duer.dcs.framework.dispatcher;

import com.baidu.duer.dcs.util.ObjectMapperUtil;

import org.codehaus.jackson.JsonProcessingException;

import java.io.IOException;


public class Parser {
    protected <T> T parse(byte[] bytes, Class<T> clazz) throws IOException {
        try {
            return ObjectMapperUtil.instance().getObjectReader().withType(clazz).readValue(bytes);
        } catch (JsonProcessingException e) {
            String unparsedContent = new String(bytes, "UTF-8");
            String message = String.format("failed to parse %1$s", clazz.getSimpleName());
            throw new DcsJsonProcessingException(message, e, unparsedContent);
        }
    }
}