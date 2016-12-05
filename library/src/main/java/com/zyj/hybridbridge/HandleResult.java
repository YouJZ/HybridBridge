package com.zyj.hybridbridge;

/**
 * dec:
 * createBy yjzhao
 * createTime 16/5/14 11:08
 */
public class HandleResult {
    private Object mData;

    public Object getData() {
        return mData;
    }

    public void setData(Object data) {
        mData = data;
    }

    public HandleResult(Object data) {
        mData = data;
    }
    public HandleResult() {
    }
}
