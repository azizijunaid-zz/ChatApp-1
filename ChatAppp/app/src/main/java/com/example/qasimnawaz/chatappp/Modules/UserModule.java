package com.example.qasimnawaz.chatappp.Modules;

/**
 * Created by Qasim Nawaz on 2/1/2017.
 */

public class UserModule {
    private String userUUid;
    private String userName;
    private String userEmail;

    public UserModule() {
    }

    public UserModule(String userName, String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public UserModule(String userUUid, String userName, String userEmail) {
        this.userUUid = userUUid;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public String getUserUUid() {
        return userUUid;
    }

    public void setUserUUid(String userUUid) {
        this.userUUid = userUUid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
