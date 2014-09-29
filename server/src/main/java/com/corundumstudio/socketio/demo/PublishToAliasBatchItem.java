package com.corundumstudio.socketio.demo;

/**
 * Created by zhanghu on 14-9-29.
 */

public class PublishToAliasBatchItem {
    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    private String a;
    private String i;


    PublishToAliasBatchItem() {

    }

    PublishToAliasBatchItem(String a, String i) {
        this.a = a;
        this.i = i;
    }
}
