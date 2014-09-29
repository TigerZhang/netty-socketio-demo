package com.corundumstudio.socketio.demo;

/**
 * Created by zhanghu on 14-9-29.
 */
public class SetAliasObject {
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    private String alias;

    public SetAliasObject() {

    }

    public SetAliasObject(String alias) {
        this.alias = alias;
    }
}
