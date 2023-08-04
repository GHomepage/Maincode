
package com.baidu.duer.dcs.devicemodule.screen;

import com.baidu.duer.dcs.devicemodule.screen.message.HtmlPayload;
import com.baidu.duer.dcs.devicemodule.screen.message.LinkClickedPayload;
import com.baidu.duer.dcs.devicemodule.screen.message.RenderVoiceInputTextPayload;
import com.baidu.duer.dcs.devicemodule.system.HandleDirectiveException;
import com.baidu.duer.dcs.framework.BaseDeviceModule;
import com.baidu.duer.dcs.framework.IMessageSender;
import com.baidu.duer.dcs.framework.message.ClientContext;
import com.baidu.duer.dcs.framework.message.Directive;
import com.baidu.duer.dcs.framework.message.Event;
import com.baidu.duer.dcs.framework.message.Header;
import com.baidu.duer.dcs.framework.message.MessageIdHeader;
import com.baidu.duer.dcs.framework.message.Payload;
import com.baidu.duer.dcs.systeminterface.IWebView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ScreenDeviceModule extends BaseDeviceModule {
    private final IWebView webView;  //IwebView接口对象
    private List<IRenderVoiceInputTextListener> listeners;
    private final List<IRenderListener> renderListeners;

    public ScreenDeviceModule(IWebView webView, IMessageSender messageSender) {
        super(ApiConstants.NAMESPACE, messageSender);
        this.webView = webView;
        webView.addWebViewListener(new IWebView.IWebViewListener() {
            @Override
            public void onLinkClicked(String url) {
                sendLinkClickedEvent(url);
            }
        });
        this.listeners = Collections.synchronizedList(new ArrayList<IRenderVoiceInputTextListener>());
        renderListeners = new ArrayList<>();
    }

    @Override
    public ClientContext clientContext() {
        return null;
    }

    @Override
    public void handleDirective(Directive directive) throws HandleDirectiveException {
        String name = directive.header.getName();
        if (name.equals(ApiConstants.Directives.HtmlView.NAME)) {
            handleHtmlPayload(directive.getPayload());
        } else if (name.equals(ApiConstants.Directives.RenderVoiceInputText.NAME)) {
            handleRenderVoiceInputTextPayload(directive.getPayload());
            handleScreenDirective(directive);
        } else if (name.equals(ApiConstants.Directives.RenderCard.NAME)) {
            handleScreenDirective(directive);
        } else if (name.equals(ApiConstants.Directives.RenderHint.NAME)) {
            handleScreenDirective(directive);
        } else {
            String message = "VoiceOutput cannot handle the directive";
            throw (new HandleDirectiveException(
                    HandleDirectiveException.ExceptionType.UNSUPPORTED_OPERATION, message));
        }
    }

    @Override
    public void release() {
        listeners.clear();
    }

    private void handleHtmlPayload(Payload payload) {
        if (payload instanceof HtmlPayload) {
            HtmlPayload htmlPayload = (HtmlPayload) payload;
            webView.loadUrl(htmlPayload.getUrl());
        }
    }

    private void handleRenderVoiceInputTextPayload(Payload payload) {
        RenderVoiceInputTextPayload textPayload = (RenderVoiceInputTextPayload) payload;
        fireRenderVoiceInputText(textPayload);
    }

    private void handleScreenDirective(Directive directive) {
        for (IRenderListener listener : renderListeners) {
            listener.onRenderDirective(directive);
        }
    }

    private void sendLinkClickedEvent(String url) {
        String name = ApiConstants.Events.LinkClicked.NAME;
        Header header = new MessageIdHeader(getNameSpace(), name);

        LinkClickedPayload linkClickedPayload = new LinkClickedPayload(url);
        Event event = new Event(header, linkClickedPayload);
        if (messageSender != null) {
            messageSender.sendEvent(event);
        }
    }

    private void fireRenderVoiceInputText(RenderVoiceInputTextPayload payload) {
        for (IRenderVoiceInputTextListener listener : listeners) {
            listener.onRenderVoiceInputText(payload);
        }
    }

    public void addRenderVoiceInputTextListener(IRenderVoiceInputTextListener listener) {
        listeners.add(listener);
    }

    public void addRenderListener(IRenderListener listener) {
        renderListeners.add(listener);
    }

    public void removeRenderListener(IRenderListener listener) {
        renderListeners.remove(listener);
    }

    public interface IRenderVoiceInputTextListener {

        void onRenderVoiceInputText(RenderVoiceInputTextPayload payload);

    }

    public interface IRenderListener {
        void onRenderDirective(Directive directive);
    }
}
