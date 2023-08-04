
package com.baidu.duer.dcs.framework;

import com.baidu.duer.dcs.devicemodule.system.HandleDirectiveException;
import com.baidu.duer.dcs.framework.message.ClientContext;
import com.baidu.duer.dcs.framework.message.Directive;

public abstract class BaseDeviceModule {
    private final String nameSpace;
    protected final IMessageSender messageSender;

    public BaseDeviceModule(String nameSpace) {
        this(nameSpace, null);
    }

    public BaseDeviceModule(String nameSpace, IMessageSender messageSender) {
        this.nameSpace = nameSpace;
        this.messageSender = messageSender;
    }


    public abstract ClientContext clientContext();

    public abstract void handleDirective(Directive directive) throws HandleDirectiveException;

    public abstract void release();

    public String getNameSpace() {
        return nameSpace;
    }
}