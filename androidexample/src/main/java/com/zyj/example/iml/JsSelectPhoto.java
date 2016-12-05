package com.zyj.example.iml;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;

import com.google.gson.Gson;
import com.zyj.example.entity.JsMsgPhotoEntity;

import com.zyj.hybridbridge.JsAction;

/**
 * dec:选择照片处理
 * createBy yjzhao
 * createTime 16/5/14 11:08
 */
public class JsSelectPhoto extends JsAction {
    public static final String ACTION="selectphoto";

    @Override
    protected void handleAction(Activity context, String jsonStr) {
        JsMsgPhotoEntity photoEntity = new Gson().fromJson(jsonStr, JsMsgPhotoEntity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"}, 10000);
        }
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        context.startActivityForResult(intent, JsMsgPhotoEntity.SELECTREQUESTCODE);
    }

}
