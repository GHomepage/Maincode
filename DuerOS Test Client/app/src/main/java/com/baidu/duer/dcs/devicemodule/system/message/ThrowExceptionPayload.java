
package com.baidu.duer.dcs.devicemodule.system.message;

import com.baidu.duer.dcs.framework.message.Payload;


public class ThrowExceptionPayload extends Payload {
    // 目前有以下5种错误码
    public enum Code {
        // 请求格式错误.
        INVALID_REQUEST_EXCEPTION("INVALID_REQUEST_EXCEPTION"),
        // 请求认证失败.
        UNAUTHORIZED_REQUEST_EXCEPTION("UNAUTHORIZED_REQUEST_EXCEPTION"),
        // 请求数量太多/频率太高.
        THROTTLING_EXCEPTION("THROTTLING_EXCEPTION"),
        // 服务端内部错误.
        INTERNAL_SERVICE_EXCEPTION("INTERNAL_SERVICE_EXCEPTION"),
        // 服务端不可用.
        NA("N/A");

        private String code;

        Code(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }


    private Code code;

    private String description;

    public ThrowExceptionPayload() {
    }

    public ThrowExceptionPayload(Code code, String description) {
        this.code = code;
        this.description = description;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public Code getCode() {
        return this.code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "ThrowExceptionPayload{"
                + "code=" + code
                + ", description='" + description + '\'' + '}';
    }
}