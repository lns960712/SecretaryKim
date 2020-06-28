package com.android.SecretaryKim;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
@IgnoreExtraProperties
public class ConferenceDTO implements Serializable {

    private String userId;
    private String timestamp;
    private String ConfId;
    private String title;
    private boolean finish;
    //    private ChatDTO chat;

    public boolean isFinish() { return finish; }
    public String getTimestamp() { return timestamp; }
    public String getConfId() { return ConfId; }
    public String getUserId() { return userId; }
    public String getTitle() { return title; }

    public void setFinish(boolean finish) { this.finish = finish; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public void setConfId(String confId) { ConfId = confId; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setTitle(String title) { this.title = title; }
//    public ChatDTO getChat() { return chat; }
//    public void setChat(ChatDTO chat) { this.chat = chat; }
}