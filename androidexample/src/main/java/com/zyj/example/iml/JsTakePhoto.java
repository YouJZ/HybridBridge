package com.zyj.example.iml;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.provider.MediaStore;

import com.google.gson.Gson;
import com.zyj.example.entity.JsMsgPhotoEntity;
import com.zyj.jsbridge.JsAction;

/**
 * dec:拍照处理
 * createBy yjzhao
 * createTime 16/5/14 11:08
 */
public class JsTakePhoto extends JsAction {

    public static final String ACTION="takephoto";

    @Override
    protected void handleAction(Activity context, String jsonStr) {
        JsMsgPhotoEntity photoEntity = new Gson().fromJson(jsonStr, JsMsgPhotoEntity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"}, 10000);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("autofocus",true); // 自动对焦
        context.startActivityForResult(intent, JsMsgPhotoEntity.TAKEREQUESTCODE);
    }


}
