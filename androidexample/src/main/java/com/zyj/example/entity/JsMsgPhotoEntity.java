package com.zyj.example.entity;

import com.zyj.hybridbridge.JsMessage;

/**
 * dec:拍照
 * createBy yjzhao
 * createTime 16/5/14 11:08
 */
public class JsMsgPhotoEntity extends JsMessage {
    public static final int TAKEREQUESTCODE=10001;
    public static final int SELECTREQUESTCODE=10002;


    /**
     * data : {"width":1,"height":2,"iscompress":true}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * width : 1
         * height : 2
         * iscompress : true
         */

        private int width;
        private int height;
        private boolean iscompress;

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public boolean isIscompress() {
            return iscompress;
        }

        public void setIscompress(boolean iscompress) {
            this.iscompress = iscompress;
        }
    }
}
