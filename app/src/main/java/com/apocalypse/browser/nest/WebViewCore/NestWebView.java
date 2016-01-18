package com.apocalypse.browser.nest.WebViewCore;
import android.content.Context;
import android.webkit.WebView;

/**
 * Created by Dave on 2016/1/12.
 */
class NestWebView extends WebView{

    private final String TAG = "NestWebView";
    NestWebView(Context c){
        super(c);
    }
   /**
    * 重写WebView 暂时不做事情，方便之后重构
    * @author Dave
    * Create at 2016/1/12 16:32
   **/
}
