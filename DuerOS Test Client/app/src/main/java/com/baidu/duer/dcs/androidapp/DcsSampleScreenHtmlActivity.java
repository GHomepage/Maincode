package com.baidu.duer.dcs.androidapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.duer.dcs.R;
import com.baidu.duer.dcs.androidsystemimpl.webview.BaseWebView;
import com.baidu.duer.dcs.devicemodule.screen.message.HtmlPayload;
import com.baidu.duer.dcs.util.LogUtil;

import java.io.DataInput;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class DcsSampleScreenHtmlActivity extends Activity {
    private HtmlPayload htmlPayLoad;
    private RelativeLayout relativeLayout;
    private BaseWebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dcs_sample_activity_screen_html);
        htmlPayLoad = (HtmlPayload) this.getIntent().getSerializableExtra("HTML_PLAY_LOAD");
        initView();
        addView();
    }

    private void initView() {
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
    }

    private void addView() {
        webView = new BaseWebView(DcsSampleScreenHtmlActivity.this.getApplicationContext());
        webView.setWebViewClientListen(new BaseWebView.WebViewClientListener() {
            @Override
            public BaseWebView.LoadingWebStatus shouldOverrideUrlLoading(WebView view, String url) {
                return BaseWebView.LoadingWebStatus.STATUS_UNKNOW;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            @Override
            public void onPageFinished(WebView view, String url) {

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            }
        });
        relativeLayout.addView(webView);
        if (null != htmlPayLoad) {
            webView.loadUrl(htmlPayLoad.getUrl());
        }
    }
}

