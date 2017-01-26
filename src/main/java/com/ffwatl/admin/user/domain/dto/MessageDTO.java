package com.ffwatl.admin.user.domain.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ffwatl.admin.user.domain.Message;
import com.ffwatl.admin.user.domain.User;

import java.util.List;

public class MessageDTO implements Message{

    private long id;

    @JsonDeserialize(as=UserDTO.class)
    private User fromUser;

    @JsonDeserialize(contentAs=UserDTO.class)
    private List<User> toUsers;

    private String message;

    private String topic;

    private boolean unread = true;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public User getFromUser() {
        return fromUser;
    }

    @Override
    public List<User> getToUsers() {
        return toUsers;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public boolean isUnread() {
        return unread;
    }

    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public Message setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public Message setFromUsers(User fromUser) {
        this.fromUser = fromUser;
        return this;
    }

    @Override
    public Message setToUsers(List<User> toUser) {
        this.toUsers = toUser;
        return this;
    }

    @Override
    public Message setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public Message setUnread(boolean unread) {
        this.unread = unread;
        return this;
    }

    @Override
    public Message setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageDTO that = (MessageDTO) o;

        if (getId() != that.getId()) return false;
        if (isUnread() != that.isUnread()) return false;
        if (getFromUser() != null ? !getFromUser().equals(that.getFromUser()) : that.getFromUser() != null)
            return false;
        if (getToUsers() != null ? !getToUsers().equals(that.getToUsers()) : that.getToUsers() != null) return false;
        if (getMessage() != null ? !getMessage().equals(that.getMessage()) : that.getMessage() != null) return false;
        return !(getTopic() != null ? !getTopic().equals(that.getTopic()) : that.getTopic() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getFromUser() != null ? getFromUser().hashCode() : 0);
        result = 31 * result + (getToUsers() != null ? getToUsers().hashCode() : 0);
        result = 31 * result + (getMessage() != null ? getMessage().hashCode() : 0);
        result = 31 * result + (getTopic() != null ? getTopic().hashCode() : 0);
        result = 31 * result + (isUnread() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "id=" + id +
                ", fromUser=" + fromUser +
                ", toUsers=" + toUsers +
                ", message='" + message + '\'' +
                ", topic='" + topic + '\'' +
                ", unread=" + unread +
                '}';
    }
}