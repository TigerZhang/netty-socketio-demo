package com.corundumstudio.socketio.demo;

import java.util.List;

public class PublishToAliasBatchObject {
    public PublishToAliasBatchObject() {

    }

    public PublishToAliasBatchObject(PublishToAliasBatchItem[] alias_list, String msg) {
        this.alias_list = alias_list;
        this.msg = msg;
    }

    public PublishToAliasBatchItem[] getAlias_list() {
        return alias_list;
    }

    public void setAlias_list(PublishToAliasBatchItem[] alias_list) {
        this.alias_list = alias_list;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private PublishToAliasBatchItem[] alias_list;
    private String msg;


}
