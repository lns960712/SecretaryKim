package com.android.SecretaryKim;

import com.android.SecretaryKim.DTO.UserDTO;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
@IgnoreExtraProperties
public class ChatDTO implements Serializable {
    private String message;
    private String nickname;
    private UserDTO user;
    private boolean highlight;

    public boolean isHighlight() { return highlight; }
    public UserDTO getUser() { return user; }
    public String getMessage() { return message; }
    public String getNickname() {return nickname;}

    public void setNickname(String nickname) {this.nickname = nickname;}
    public void setMessage(String message) {
        this.message = message;
    }
    public void setUser(UserDTO user) { this.user = user; }
    public void setHighlight(boolean highlight) { this.highlight = highlight; }


}