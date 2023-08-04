
package com.baidu.duer.dcs.framework;

import com.baidu.duer.dcs.framework.message.DcsStreamRequestBody;
import com.baidu.duer.dcs.framework.message.Event;


public interface IMessageSender {

    void sendEvent(Event event);


    void sendEvent(Event event, IResponseListener responseListener);

    void sendEvent(Event event, DcsStreamRequestBody streamRequestBody, IResponseListener responseListener);

    void sentEventWithClientContext(Event event, IResponseListener responseListener);
}