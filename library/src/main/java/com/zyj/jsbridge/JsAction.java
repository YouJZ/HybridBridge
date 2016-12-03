package com.zyj.jsbridge;

import android.app.Activity;
import android.webkit.WebView;

import com.google.gson.Gson;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * dec:
 * createBy yjzhao
 * createTime 16/5/14 11:08
 */
public  abstract class JsAction {
    protected abstract void handleAction(Activity context,String jsonStr);

    public void callback(final WebView webView, final String callback, final Object result){
        //切换至主线程
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                subscriber.onNext("");
            }
        }).subscribeOn(Schedulers.immediate())
        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                if (null==result||null==callback||"".equals(callback))return;
               String resultStr= new Gson().toJson(result);
                String url = "javascript:"+callback+"("+resultStr+")";
                webView.loadUrl(url);

            }
        });


    }
}
