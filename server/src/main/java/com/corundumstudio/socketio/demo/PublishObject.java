package com.corundumstudio.socketio.demo;

/**
 * Created by zhanghu on 14-9-28.
 */
public class PublishObject {
    private String topic;
    private String msg;
    private String messageId;
    private int qos = 1;

    public PublishObject() {

    }

    public PublishObject(String topic, String msg, String messageId, int qos) {
        this.topic = topic;
        this.msg = msg;
        this.messageId = messageId;
        this.qos = qos;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public int getQos() {
        return qos;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }
}
