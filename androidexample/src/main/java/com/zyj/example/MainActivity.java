package com.zyj.example;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.webkit.WebView;

import com.zyj.example.entity.JsMsgPhotoEntity;
import com.zyj.example.entity.PhotoEntity;
import com.zyj.example.iml.JsCall;
import com.zyj.example.iml.JsDeviceInfo;
import com.zyj.example.iml.JsSelectPhoto;
import com.zyj.example.iml.JsTakePhoto;
import com.zyj.jsbridge.HandleResult;
import com.zyj.jsbridge.JsBridge;
import com.zyj.jsbridge.RxBus;

import static com.zyj.example.Utils.saveBitmapToFile;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView webView = ((WebView) findViewById(R.id.webView));
        webView.loadUrl("file:///android_asset/demo.html");
        JsBridge.getInstance().init(this, webView)
                .addJsAction(JsCall.ACTION, JsCall.class)
                .addJsAction(JsSelectPhoto.ACTION, JsSelectPhoto.class)
                .addJsAction(JsTakePhoto.ACTION, JsTakePhoto.class)
                .addJsAction(JsDeviceInfo.ACTION, JsDeviceInfo.class);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == JsMsgPhotoEntity.TAKEREQUESTCODE) {
            takePhoto(data);
        } else if (requestCode == JsMsgPhotoEntity.SELECTREQUESTCODE) {
            selectPhoto(data);
        }
    }
    /**
     * 拍照
     *
     * @param data 图片数据
     */
    private void takePhoto(Intent data) {
        Bundle bundle = data.getExtras();
        Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
        String imagePath = saveBitmapToFile(bitmap,null);
        String smallImagePath = saveBitmapToFile(Utils.getSmallBitmap(imagePath),"1");

        PhotoEntity photoEntity =new PhotoEntity();
        photoEntity.setImagePath(imagePath);
        photoEntity.setSmImagePath(smallImagePath);
        RxBus.getInstance().post( new HandleResult(photoEntity));
    }
    /**
     * 图库选择
     *
     * @param data 图片数据
     */
    private void selectPhoto(Intent data) {
        Uri selectImageUri = data.getData();
        String[] filePathColumn = new String[]{MediaStore.Images.Media.DATA};//要查询的列
        Cursor cursor = getContentResolver().query(selectImageUri, filePathColumn, null, null, null);
        String pirPath = null;
        String smallImagePath=null;
        while (cursor.moveToNext()) {
            pirPath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));//所选择的图片路径
            smallImagePath = saveBitmapToFile(Utils.getSmallBitmap(pirPath),null);
        }
        cursor.close();
        PhotoEntity photoEntity =new PhotoEntity();
        photoEntity.setImagePath(pirPath);
        photoEntity.setSmImagePath(smallImagePath);
        RxBus.getInstance().post(new HandleResult(photoEntity));
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        JsBridge.getInstance().destroy();
    }
}
