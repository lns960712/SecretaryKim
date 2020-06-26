package com.android.SecretaryKim;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
@IgnoreExtraProperties
public class ConferenceDTO implements Serializable {

    private String userId;
    private String timestamp;
    private String ConfId;
    private String title;
//    private ChatDTO chat;

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public String getConfId() { return ConfId; }
    public void setConfId(String confId) { ConfId = confId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
//    public ChatDTO getChat() { return chat; }
//    public void setChat(ChatDTO chat) { this.chat = chat; }
}