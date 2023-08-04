
package com.baidu.duer.dcs.framework.message;


public class DialogRequestIdHeader extends MessageIdHeader {

    private String dialogRequestId;

    public DialogRequestIdHeader() {

    }

    public DialogRequestIdHeader(String nameSpace, String name, String dialogRequestId) {
        super(nameSpace, name);
        this.dialogRequestId = dialogRequestId;
    }

    public final String getDialogRequestId() {
        return dialogRequestId;
    }

    public final void setDialogRequestId(String dialogRequestId) {
        this.dialogRequestId = dialogRequestId;
    }

    @Override
    public String toString() {
        return String.format("%1$s dialogRequestId:%2$s", super.toString(), dialogRequestId);
    }
}