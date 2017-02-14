package com.example.qasimnawaz.chatappp.Modules;

/**
 * Created by Qasim Nawaz on 2/2/2017.
 */

public class ConversationModule {
    private String pushKey;
    private String uuid;

    public ConversationModule() {
    }

    public ConversationModule(String pushKey, String uuid) {
        this.pushKey = pushKey;
        this.uuid = uuid;
    }

    public String getPushKey() {
        return pushKey;
    }

    public void setPushKey(String pushKey) {
        this.pushKey = pushKey;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
