package com.corundumstudio.socketio.demo;

import com.corundumstudio.socketio.listener.*;
import com.corundumstudio.socketio.*;
import org.json.JSONObject;
import org.json.JSONString;

import java.util.logging.Logger;

public class ChatLauncher {
    // assumes the current class is called logger
    private final static Logger LOGGER = Logger.getLogger("ChatLauncher");

    public static void main(String[] args) throws InterruptedException {

        Configuration config = new Configuration();
        config.setHostname("0.0.0.0");
        config.setPort(9092);


        final SocketIOServer server = new SocketIOServer(config);

        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                LOGGER.info(String.format("%s, %s", client.getSessionId(), "connected"));

                // TODO
                // 1. create mqtt client
                //   1.1 try to get reusable user info
                //   1.2 reg a new user if necessary
                //   1.3 connect to broker
                // 2. add mqtt client to clientList

                client.sendEvent("socketconnectack", "{ msg: 'socket.io connected' }");
            }
        });

        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                LOGGER.info(String.format("%s, %s", client.getSessionId(), "disconnected"));

                // TODO
                // 1. disconnect mqtt client
                // 2. add the freed user info to reusable list
            }
        });

        server.addEventListener("chatevent", ChatObject.class, new DataListener<ChatObject>() {
            @Override
            public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
                // broadcast messages to all clients
                LOGGER.info(String.format("%s, %s", client.getSessionId(), String.format(data.getMessage())));

                server.getBroadcastOperations().sendEvent("chatevent", data);
            }
        });

        server.addEventListener("connect", ConnectObject.class, new DataListener<ConnectObject>() {
            @Override
            public void onData(SocketIOClient client, ConnectObject data, AckRequest ackRequest) throws Exception {
                LOGGER.info(data.getAppkey());

                JSONObject ack = new JSONObject();
                ack.put("success", true);

                client.sendEvent("connack", "{sucess: true}");
            }
        });

        server.addEventListener("subscribe", SubscribeObject.class, new DataListener<SubscribeObject>() {
            @Override
            public void onData(SocketIOClient client, SubscribeObject data, AckRequest ackSender) throws Exception {
                LOGGER.info("subscribe " + data.getTopic());

                client.sendEvent("suback", "{topic: data.topic, success: success}");
            }
        });

        server.addEventListener("publish", PublishObject.class, new DataListener<PublishObject>() {
            @Override
            public void onData(SocketIOClient client, PublishObject data, AckRequest ackSender) throws Exception {
                LOGGER.info(String.format("publish %s, %s, %s", data.getTopic(), data.getMsg(), data.getMessageId()));
                LOGGER.info(String.format("%s", data.getQos()));

                client.sendEvent("puback", "{success: true, \"messageId\": msgId.toString()}");
            }
        });

        server.addEventListener("set_alias", SetAliasObject.class, new DataListener<SetAliasObject>() {
            @Override
            public void onData(SocketIOClient client, SetAliasObject data, AckRequest ackSender) throws Exception {
                LOGGER.info(String.format("set_alias %s", data.getAlias()));

                client.sendEvent("set_alias_ack", "{success: true}");
            }
        });

        server.addEventListener("publish_to_alias", PublishToAliasObject.class, new DataListener<PublishToAliasObject>() {
            @Override
            public void onData(SocketIOClient client, PublishToAliasObject data, AckRequest ackSender) throws Exception {
                LOGGER.info(String.format("publish_to_alias %s, %s", data.getAlias(), data.getMsg()));

                client.sendEvent("puback", "{success: true, \"messageId\": msgId.toString()}");
            }
        });

        server.addEventListener("publish_to_alias_batch", PublishToAliasBatchObject.class, new DataListener<PublishToAliasBatchObject>() {
            @Override
            public void onData(SocketIOClient client, PublishToAliasBatchObject data, AckRequest ackSender) throws Exception {
                LOGGER.info(String.format("publish_to_alias_batch"));
                for (int i = 0; i < data.getAlias_list().length; i++) {
                    LOGGER.info(String.format("item %s, %s", data.getAlias_list()[i].getA(), data.getAlias_list()[i].getI()));

                    client.sendEvent("puback", "{success: true, \"messageId\": msgId.toString()}");
                }
            }
        });

        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }

}
