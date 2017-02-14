package com.example.qasimnawaz.chatappp.Modules;

/**
 * Created by Qasim Nawaz on 2/2/2017.
 */

public class MessageModule {
    private String messageText;
    private String photoUrl;
    private String messageUUID;
    private String messageTime;

    public MessageModule() {
    }

    public MessageModule(String messageText, String messageUUID, String messageTime) {
        this.messageText = messageText;
        this.messageUUID = messageUUID;
        this.messageTime = messageTime;
    }

    public MessageModule(String messageText, String messageTime) {
        this.messageText = messageText;
        this.messageTime = messageTime;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getMessageUUID() {
        return messageUUID;
    }

    public void setMessageUUID(String messageUUID) {
        this.messageUUID = messageUUID;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }
}
