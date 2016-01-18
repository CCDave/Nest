package com.apocalypse.browser.nest.WebViewCore;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.apocalypse.browser.nest.utils.SimpleLog;

/**
 * Created by Dave on 2016/1/12.
 */
class NestWebChromeClient extends WebChromeClient {

    private static final String TAG = "InnerClass-NestWebChromeClient";
    private IWebCoreDelegate mWebCoreDelegate;

    public NestWebChromeClient(IWebCoreDelegate webCoreDelegate) {
        super();
        mWebCoreDelegate = webCoreDelegate;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {

        SimpleLog.d(TAG, "onProgressChanged");
        mWebCoreDelegate.onProgressChanged(view, newProgress);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        SimpleLog.d(TAG, "onReceivedTitle");
        mWebCoreDelegate.onReceivedTitle(view, title);
    }

    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        SimpleLog.d(TAG, "onReceivedIcon");
        super.onReceivedIcon(view, icon);
    }

    @Override
    public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
        SimpleLog.d(TAG, "onReceivedTouchIconUrl");
        super.onReceivedTouchIconUrl(view, url, precomposed);
    }

    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        SimpleLog.d(TAG, "onShowCustomView");
        super.onShowCustomView(view, callback);
    }

    @Override
    public void onHideCustomView() {
        SimpleLog.d(TAG, "onHideCustomView");
        super.onHideCustomView();
    }

    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        SimpleLog.d(TAG, "onCreateWindow");
        return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
    }

    @Override
    public void onRequestFocus(WebView view) {
        SimpleLog.d(TAG, "onRequestFocus");
        super.onRequestFocus(view);
    }

    @Override
    public void onCloseWindow(WebView window) {
        SimpleLog.d(TAG, "onCloseWindow");
        super.onCloseWindow(window);
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        SimpleLog.d(TAG, "onJsAlert");
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        SimpleLog.d(TAG, "onJsConfirm");
        return super.onJsConfirm(view, url, message, result);
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        SimpleLog.d(TAG, "onJsPrompt");
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    @Override
    public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
        SimpleLog.d(TAG, "onJsBeforeUnload");
        return super.onJsBeforeUnload(view, url, message, result);
    }

    @Override
    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
        SimpleLog.d(TAG, "onGeolocationPermissionsShowPrompt");
        super.onGeolocationPermissionsShowPrompt(origin, callback);
    }

    @Override
    public void onGeolocationPermissionsHidePrompt() {
        SimpleLog.d(TAG, "onGeolocationPermissionsHidePrompt");
        super.onGeolocationPermissionsHidePrompt();
    }

    @Override
    public void onPermissionRequest(PermissionRequest request) {
        SimpleLog.d(TAG, "onPermissionRequest");
        super.onPermissionRequest(request);
    }

    @Override
    public void onPermissionRequestCanceled(PermissionRequest request) {
        SimpleLog.d(TAG, "onPermissionRequestCanceled");
        super.onPermissionRequestCanceled(request);
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        SimpleLog.d(TAG, "onConsoleMessage");
        return super.onConsoleMessage(consoleMessage);
    }

    @Override
    public Bitmap getDefaultVideoPoster() {
        SimpleLog.d(TAG, "getDefaultVideoPoster");
        return super.getDefaultVideoPoster();
    }

    @Override
    public View getVideoLoadingProgressView() {
        SimpleLog.d(TAG, "getVideoLoadingProgressView");
        return super.getVideoLoadingProgressView();
    }

    @Override
    public void getVisitedHistory(ValueCallback<String[]> callback) {
        SimpleLog.d(TAG, "getVisitedHistory");
        super.getVisitedHistory(callback);
    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        SimpleLog.d(TAG, "onShowFileChooser");
        return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
    }
}
