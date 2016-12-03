package com.zyj.example;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * dec:
 * createBy yjzhao
 * createTime 16/5/14 11:08
 */
public class Utils {
    public static String saveBitmapToFile(Bitmap bitmap, String name){
        @SuppressLint("SimpleDateFormat")
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + (null==name?"":name)+".png";
        File photoFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), fileName);
        try {
            FileOutputStream b =new FileOutputStream(photoFile);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,b);
            b.flush();
            b.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return photoFile.getAbsolutePath();
    }
    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }
}
