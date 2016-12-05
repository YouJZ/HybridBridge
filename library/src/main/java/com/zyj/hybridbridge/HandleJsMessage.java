package com.zyj.hybridbridge;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.webkit.WebView;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import rx.Subscription;
import rx.functions.Action1;

/**
 * dec:处理消息类
 * createBy yjzhao
 * createTime 16/5/14 11:08
 */
public class HandleJsMessage {
    private Activity mContext;
    private WebView mWebView;
    private Map<String, Class<? extends JsAction>> mActionMap;
    private JsAction mJsAction;
    private String jsCallback;
    private Subscription mJsSubscription;

    public HandleJsMessage() {
        mActionMap = new HashMap<>();

    }

    public HandleJsMessage(Activity context, WebView webView) {
        this();
        mContext = context;
        mWebView = webView;
        mJsSubscription = RxBus.getInstance().toObservable(HandleResult.class).subscribe(new Action1<HandleResult>() {
            @Override
            public void call(HandleResult result) {
                mJsAction.callback(mWebView, jsCallback, result.getData());
            }
        });
    }

    /**
     *  处理js传递过来的数据
     * @param jsonStr js传递的数据
     * @return 是否处理
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    boolean handle(String jsonStr) {
        JsMessage jsMessage = new Gson().fromJson(jsonStr, JsMessage.class);
        String action = jsMessage.getAction();
        jsCallback = jsMessage.getCallback();
        if (null == jsMessage.getAction())
            return false;
        if (HandleAction(jsonStr, action, mActionMap)) return true;
        return false;
    }

    /**
     *  根据js传递过来的action将事件分发下去
     * @param jsonStr js传递的数据
     * @param action js意图
     * @param map js意图集合
     * @return 是否处理存在处理次意图的接口
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private boolean HandleAction(String jsonStr, String action, Map<String, Class<? extends JsAction>> map) {
        for (String mapAction : map.keySet()) {
            if (mapAction.equals(action)) {
                try {
                    mJsAction = map.get(mapAction).newInstance();
                    if (mJsAction != null) {
                        mJsAction.handleAction(mContext, jsonStr);
                    }
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }

    public Map<String, Class<? extends JsAction>> getActionMap() {
        return mActionMap;
    }

    public void destroy() {
        if (mJsSubscription!=null)mJsSubscription.unsubscribe();
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
        }
        if (mContext != null) mContext = null;
    }
}
