package com.android.SecretaryKim;

import java.io.Serializable;

public class UserDTO implements Serializable {

    private String userId;
    private String email;
    private String nickname;

    public String getUid() {return userId;}
    public String getEmail() { return email; }
    public String getNickname() {
        return nickname;
    }

    public void setUid(String userId) {this.userId = userId;}
    public void setEmail(String email) { this.email = email; }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
