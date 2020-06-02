package com.android.SecretaryKim;

public class ConferenceDTO {

    private UserDTO user;
    private String timestamp;
    private String ConfId;
    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public String getConfId() {
        return ConfId;
    }
    public void setConfId(String confId) {
        ConfId = confId;
    }
}
