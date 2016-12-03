package com.zyj.jsbridge;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * dec:
 * createBy yjzhao
 * createTime 16/5/14 11:08
 */
public class JsBridge {

    private HandleJsMessage mHandleJsMessage;
    private JsBridge(){}

    public static JsBridge getInstance(){
        return JsBridgeHolder.getJsBridgeInstance;

    }
    private static final class JsBridgeHolder{
        private final static JsBridge getJsBridgeInstance = new JsBridge();
    }
    public JsBridge addJsAction( @Nullable String action,@Nullable Class<? extends  JsAction> jsActionImp ){
        mHandleJsMessage.getActionMap().put(action,jsActionImp);
        return this;
    }


    public JsBridge init(@Nullable Activity context,@Nullable WebView webView){
        mHandleJsMessage =new HandleJsMessage(context,webView);
        setting(context, webView);
        return this;


    }

    private void setting(Context context, WebView webView) {
        //设置加载到webView的页面是否可以使用chrome上调试(调试阶段)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        WebSettings webSettings = webView.getSettings();
        //设置可与js交互
        webSettings.setJavaScriptEnabled(true);
        // 可以读取文件缓存(manifest生效)
        webSettings.setAllowFileAccess(true);
        // 设置可以使用localStorage
        webSettings.setDomStorageEnabled(true);
        // 应用可以有数据库
        webSettings.setDatabaseEnabled(true);
        String dbPath = context.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setDatabasePath(dbPath);
        // 启用地理定位
        webSettings.setGeolocationEnabled(true);
        // 设置定位的数据库路径
        webSettings.setGeolocationDatabasePath(dbPath);
        // 设置编码格式
        webSettings.setDefaultTextEncodingName("utf-8");
        // 设置了这个，页面中就不会出现两边白边了
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        // 支持跨域请求
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            webSettings.setAllowUniversalAccessFromFileURLs(true);
        // 应用可以有缓存
        webSettings.setAppCacheEnabled(true);
        String appCaceDir = context.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        webSettings.setAppCachePath(appCaceDir);
        // 默认使用缓存
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 设置缓存最多可以有8M
        webSettings.setAppCacheMaxSize(8 * 1024 * 1024);
        addJavascriptInterface(webView);


    }


    @SuppressLint({"JavascriptInterface", "AddJavascriptInterface"})
    private void addJavascriptInterface(WebView webView) {
        webView.addJavascriptInterface(new Object(){
            @JavascriptInterface
            public void sendMessage(String jsonStr){
                mHandleJsMessage.handle(jsonStr);
            }
        },"native");
    }


    public void destroy(){
        mHandleJsMessage.destroy();
    }


}
