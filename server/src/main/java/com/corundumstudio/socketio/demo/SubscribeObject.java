package com.corundumstudio.socketio.demo;

/**
 * Created by zhanghu on 14-9-28.
 */
public class SubscribeObject {
    private String topic;

    public SubscribeObject() {

    }

    public SubscribeObject(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
