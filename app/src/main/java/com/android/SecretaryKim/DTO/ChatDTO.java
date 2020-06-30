package com.android.SecretaryKim.DTO;

import com.android.SecretaryKim.DTO.UserDTO;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
@IgnoreExtraProperties
public class ChatDTO implements Serializable {
    private String message;//채팅
    private String nickname;//유저닉네임
    private UserDTO user;//유져정보
    private boolean highlight;//강조채팅표시

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