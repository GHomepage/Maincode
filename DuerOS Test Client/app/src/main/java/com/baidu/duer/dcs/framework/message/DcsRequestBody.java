
package com.baidu.duer.dcs.framework.message;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class DcsRequestBody {
    private ArrayList<ClientContext> clientContext;
    private Event event;

    public DcsRequestBody(Event event) {
        this.event = event;
    }

    public void setClientContext(ArrayList<ClientContext> clientContexts) {
        this.clientContext = clientContexts;
    }

    public ArrayList<ClientContext> getClientContext() {
        return clientContext;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }
}