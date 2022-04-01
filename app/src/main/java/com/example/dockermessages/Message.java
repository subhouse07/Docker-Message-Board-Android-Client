package com.example.dockermessages;

public class Message {
    private final String id;
    private final String sender;
    private final String msgText;

    public Message(String id, String sender, String msgText) {
        this.id = id;
        this.sender = sender;
        this.msgText = msgText;
    }

    public String getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getMsgText() {
        return msgText;
    }
}
