package com.android.SecretaryKim;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
@IgnoreExtraProperties
public class ChatDTO implements Serializable {
    private String message;
    private String nickname;
    private UserDTO user;

    public String getNickname() {return nickname;}
    public void setNickname(String nickname) {this.nickname = nickname;}
    public String getMessage() { return message; }
    public void setMessage(String message) {
        this.message = message;
    }
    public UserDTO getUser() { return user; }
    public void setUser(UserDTO user) { this.user = user; }

}