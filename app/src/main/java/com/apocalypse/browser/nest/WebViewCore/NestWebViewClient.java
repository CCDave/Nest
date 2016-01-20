package com.apocalypse.browser.nest.WebViewCore;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.view.InputEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.apocalypse.browser.nest.utils.SimpleLog;

/**
 * Created by Dave on 2016/1/12.
 */
class NestWebViewClient extends WebViewClient {
    private static final String TAG = "InnerClass-NestWebViewClient";
    private IWebCoreCallBack mWebCoreDelegate;

    NestWebViewClient(IWebCoreCallBack webCoreDelegate){
        super();
        mWebCoreDelegate = webCoreDelegate;
    }
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        SimpleLog.d(TAG, "shouldOverrideUrlLoading: " + url);
        return false;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        SimpleLog.d(TAG, "onPageFinished");
        mWebCoreDelegate.onPageFinished(view, url);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
       // SimpleLog.d(TAG, "onLoadResource");
        super.onLoadResource(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        SimpleLog.d(TAG, "onPageStarted");
        mWebCoreDelegate.onPageStarted(view, url, favicon);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        SimpleLog.d(TAG, "shouldInterceptRequest");
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public void onPageCommitVisible(WebView view, String url) {
        SimpleLog.d(TAG, "onPageCommitVisible");
        super.onPageCommitVisible(view, url);
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        SimpleLog.d(TAG, "onReceivedError");
        super.onReceivedError(view, request, error);
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        SimpleLog.d(TAG, "onReceivedHttpError");
        super.onReceivedHttpError(view, request, errorResponse);
    }

    @Override
    public void onFormResubmission(WebView view, Message dontResend, Message resend) {
        SimpleLog.d(TAG, "onFormResubmission");
        super.onFormResubmission(view, dontResend, resend);
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        SimpleLog.d(TAG, "doUpdateVisitedHistory");
        super.doUpdateVisitedHistory(view, url, isReload);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        SimpleLog.d(TAG, "onReceivedSslError");
        super.onReceivedSslError(view, handler, error);
    }

    @Override
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
        SimpleLog.d(TAG, "onReceivedClientCertRequest");
        super.onReceivedClientCertRequest(view, request);
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        SimpleLog.d(TAG, "onReceivedHttpAuthRequest");
        super.onReceivedHttpAuthRequest(view, handler, host, realm);
    }

    @Override
    public void onUnhandledInputEvent(WebView view, InputEvent event) {
        SimpleLog.d(TAG, "onUnhandledInputEvent");
        super.onUnhandledInputEvent(view, event);
    }

    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        SimpleLog.d(TAG, "onScaleChanged");
        super.onScaleChanged(view, oldScale, newScale);
    }

    @Override
    public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
        SimpleLog.d(TAG, "onReceivedLoginRequest");
        super.onReceivedLoginRequest(view, realm, account, args);
    }

}
