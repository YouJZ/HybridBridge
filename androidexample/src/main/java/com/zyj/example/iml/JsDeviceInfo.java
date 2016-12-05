package com.zyj.example.iml;

import android.app.Activity;

import com.zyj.example.entity.DeviceInfoEntity;
import com.zyj.hybridbridge.HandleResult;
import com.zyj.hybridbridge.JsAction;
import com.zyj.hybridbridge.RxBus;

/**
 * dec:设备信息接口
 * createBy yjzhao
 * createTime 16/5/14 11:08
 */
public class JsDeviceInfo extends JsAction {
    public static final String ACTION = "deviceinfo";

    @Override
    protected void handleAction(Activity context, String jsonStr) {
        HandleResult resultEntity =new HandleResult();
        DeviceInfoEntity deviceInfoEntity =new DeviceInfoEntity();
        deviceInfoEntity.setDeviceName("我的Android客户端！");
        resultEntity.setData(deviceInfoEntity);
        RxBus.getInstance().post(resultEntity);
    }
}
