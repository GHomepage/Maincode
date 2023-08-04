
package com.baidu.duer.dcs.oauth.api;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.baidu.duer.dcs.androidsystemimpl.webview.BaseWebView;
import com.baidu.duer.dcs.util.LogUtil;

public class BaiduDialog extends Dialog {
    private static final FrameLayout.LayoutParams MATCH = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
    private static final String LOG_TAG = "BaiduDialog";
    private final String mUrl;
    private final BaiduDialogListener mListener;
    private ProgressDialog mSpinner;
    private BaseWebView mWebView;
    private FrameLayout mContent;
    private RelativeLayout webViewContainer;
    private boolean isLoading;

    public BaiduDialog(Context context, String url, BaiduDialogListener listener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        mUrl = url;
        mListener = listener;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        mListener.onCancel();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置ProgressDialog的样式
        mSpinner = new ProgressDialog(getContext());
        mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mSpinner.setMessage("登录中...");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContent = new FrameLayout(getContext());
        setUpWebView();
        addContentView(mContent, MATCH);
    }

    private void setUpWebView() {
        webViewContainer = new RelativeLayout(getContext());
        mWebView = new BaseWebView(getContext().getApplicationContext());
        mWebView.setWebViewClient(new BdWebViewClient());
        mWebView.setWebChromeClient(new BdWebChromeClient());
        mWebView.loadUrl(mUrl);
        mWebView.setLayoutParams(MATCH);
        mWebView.setVisibility(View.INVISIBLE);
        webViewContainer.addView(mWebView);
        mContent.addView(webViewContainer, MATCH);
    }

    private class BdWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogUtil.d(LOG_TAG, "Redirect URL: " + url);

            if (url.startsWith(BaiduOauthImplicitGrant.SUCCESS_URI)) {
                Bundle values = OauthNetUtil.parseUrl(url);
                if (values != null && !values.isEmpty()) {
                    String error = values.getString("error");
                    // 用户取消授权返回error=access_denied
                    if ("access_denied".equals(error)) {
                        mListener.onCancel();
                        BaiduDialog.this.dismiss();
                        return true;
                    }
                    // 请求出错时返回error=1100&errorDesp=error_desp
                    String errorDesp = values.getString("error_description");
                    if (error != null && errorDesp != null) {
                        mListener.onBaiduException(new BaiduException(error, errorDesp));
                        BaiduDialog.this.dismiss();
                        return true;
                    }
                    mListener.onComplete(values);
                    BaiduDialog.this.dismiss();
                    return true;
                }
            } else if (url.startsWith(BaiduOauthImplicitGrant.CANCEL_URI)) {
                mListener.onCancel();
                BaiduDialog.this.dismiss();
                return true;
            }
            return false;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description,
                                    String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            mListener.onError(new BaiduDialogError(description, errorCode, failingUrl));
            BaiduDialog.this.dismiss();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            LogUtil.d(LOG_TAG, "Webview loading URL: " + url);
            super.onPageStarted(view, url, favicon);
            mSpinner.show();
            isLoading = true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    private class BdWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            LogUtil.d(LOG_TAG, "Webview loading newProgress: " + newProgress);
            if (newProgress > 50 && isLoading) {
                mSpinner.dismiss();
                isLoading = false;
                mContent.setBackgroundColor(Color.TRANSPARENT);
                mWebView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mSpinner.isShowing()) {
            mSpinner.dismiss();
        }
        isLoading = false;
        webViewContainer.removeView(mWebView);
        mWebView.removeAllViews();
        mWebView.destroy();
    }

    public interface BaiduDialogListener {

        void onComplete(Bundle values);


        void onBaiduException(BaiduException e);


        void onError(BaiduDialogError e);


        void onCancel();
    }
}