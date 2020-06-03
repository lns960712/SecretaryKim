package com.android.SecretaryKim;

import java.io.Serializable;

public class ChatDTO implements Serializable {
    private String message;
    private UserDTO user;

    public String getMessage() { return message; }
    public void setMessage(String message) {
        this.message = message;
    }
    public UserDTO getUser() { return user; }
    public void setUser(UserDTO user) { this.user = user; }

}