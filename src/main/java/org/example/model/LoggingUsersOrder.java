package org.example.model;

public class LoggingUsersOrder {

    private String id;
    private String userId;
    private String action;
    private String date;

    @Override
    public String toString() {
        return "{" +
                "id:'" + id + '\'' +
                ", userId:'" + userId + '\'' +
                ", action:'" + action + '\'' +
                ", date:'" + date + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getAction() {
        return action;
    }

    public String getDate() {
        return date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
