package com.android.SecretaryKim;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.List;

@IgnoreExtraProperties
public class ConferenceDTO implements Serializable {



    private List<String> joinedUserId;//참여유저를 위한 리스트
    private String userId;//방장표시를 위한 리스트
    private String timestamp;//회의방 생성시간
    private String ConfId;//회의방 고유아이디
    private String title;//회의방 제목
    private boolean finish;//회의종료여부표시
    //    private ChatDTO chat;

    public List<String> getJoinedUserId() { return joinedUserId; }
    public String getUserId() { return userId; }
    public String getTimestamp() { return timestamp; }
    public String getConfId() { return ConfId; }
    public String getTitle() { return title; }
    public boolean isFinish() { return finish; }

    public void setJoinedUserId(List<String> joinedUserId) { this.joinedUserId = joinedUserId; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public void setConfId(String confId) { ConfId = confId; }
    public void setTitle(String title) { this.title = title; }
    public void setFinish(boolean finish) { this.finish = finish; }

    public void addJoinedUserId(String userId) { this.joinedUserId.add(userId); }

//    public ChatDTO getChat() { return chat; }
//    public void setChat(ChatDTO chat) { this.chat = chat; }
}