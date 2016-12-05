package com.zyj.example.entity;

import com.zyj.hybridbridge.JsMessage;

/**
 * dec:拍照
 * createBy yjzhao
 * createTime 16/5/14 11:08
 */
public class JsMsgCallEntity extends JsMessage {
    public static final int CALLCODE=10003;


    /**
     * data : {"number":1}
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
         * number : 1
         */

        private int number;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }
}
