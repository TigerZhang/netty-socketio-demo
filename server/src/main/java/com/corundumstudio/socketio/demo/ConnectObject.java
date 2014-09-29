package com.corundumstudio.socketio.demo;

/**
 * Created by zhanghu on 14-9-28.
 */
public class ConnectObject {
    private String appkey;

    public ConnectObject() {

    }
    public ConnectObject(String appkey) {
        this.appkey = appkey;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }
}
