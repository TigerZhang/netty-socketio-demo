package com.corundumstudio.socketio.demo;

/**
 * Created by zhanghu on 14-9-28.
 */
public class PublishToAliasObject {
    private String alias;
    private String msg;
    private String messageId;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public PublishToAliasObject() {

    }

    public PublishToAliasObject(String alias, String msg) {
        this.alias = alias;
        this.msg = msg;
    }
}
