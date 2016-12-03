package com.zyj.example.iml;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.google.gson.Gson;
import com.zyj.example.entity.JsMsgCallEntity;
import com.zyj.jsbridge.JsAction;

/**
 * dec:拨打电话
 * createBy yjzhao
 * createTime 16/5/14 11:08
 */
public class JsCall extends JsAction {
    public static final String ACTION="call";


    @Override
    protected void handleAction(Activity context, String jsonStr) {
        JsMsgCallEntity msgCallEntity = new Gson().fromJson(jsonStr, JsMsgCallEntity.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 10001);
        }
        Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + msgCallEntity.getData().getNumber()));
        //启动
        context.startActivity(phoneIntent);
    }

}
