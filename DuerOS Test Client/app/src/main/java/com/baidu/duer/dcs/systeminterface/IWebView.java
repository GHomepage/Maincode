
package com.baidu.duer.dcs.systeminterface;


public interface IWebView {
    void loadUrl(String url);
    void linkClicked(String url);

    void addWebViewListener(IWebViewListener listener);

    interface IWebViewListener {
        void onLinkClicked(String url);
    }
}
