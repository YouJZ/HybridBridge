package com.zyj.hybridbridge;

/**
 * dec:
 * createBy yjzhao
 * createTime 16/5/14 11:08
 */
public class JsMessage {

    /**
     * action : js的意图
     * callback : native处理后调用js
     */

    private String action;
    private String callback;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }
}
