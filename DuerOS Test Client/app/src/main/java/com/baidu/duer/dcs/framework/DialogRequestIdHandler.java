
package com.baidu.duer.dcs.framework;

import java.util.UUID;

public class DialogRequestIdHandler {
    private String activeDialogRequestId;

    public DialogRequestIdHandler() {
    }

    public String createActiveDialogRequestId() {
        activeDialogRequestId = UUID.randomUUID().toString();
        return activeDialogRequestId;
    }

    public Boolean isActiveDialogRequestId(String dialogRequestId) {
        return activeDialogRequestId != null && activeDialogRequestId.equals(dialogRequestId);
    }
}
