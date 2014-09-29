package com.corundumstudio.socketio.demo;

import com.corundumstudio.socketio.listener.*;
import com.corundumstudio.socketio.*;

import java.util.logging.Logger;

public class ChatLauncher {
    // assumes the current class is called logger
    private final static Logger LOGGER = Logger.getLogger("ChatLauncher");

    public static void main(String[] args) throws InterruptedException {

        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);


        final SocketIOServer server = new SocketIOServer(config);

        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                LOGGER.info(String.format("%s, %s", client.getSessionId(), "connected"));

                client.sendEvent("socketconnectack", "{ msg: 'socket.io connected' }");
            }
        });

        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                LOGGER.info(String.format("%s, %s", client.getSessionId(), "disconnected"));
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

                client.sendEvent("connack", "{success: true}");
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
                }
            }
        });
        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }

}
