package com.ffwatl.admin.user.domain.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ffwatl.admin.user.domain.Message;
import com.ffwatl.admin.user.domain.User;

import java.util.List;

public class MessageDTO implements Message{

    private long id;
    @JsonDeserialize(as=UserDTO.class)
    private User from;
    @JsonDeserialize(contentAs=UserDTO.class)
    private List<User> to;
    private String message;
    private String topic;

    private boolean unread = true;
    @Override
    public long getId() {
        return 0;
    }

    @Override
    public String getTopic() {
        return null;
    }

    @Override
    public User getFrom() {
        return null;
    }

    @Override
    public boolean isUnread() {
        return false;
    }

    @Override
    public List<User> getTo() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public Message setId(long id) {
        return null;
    }

    @Override
    public Message setFrom(User from) {
        return null;
    }

    @Override
    public Message setTo(List<User> to) {
        return null;
    }

    @Override
    public Message setMessage(String message) {
        return null;
    }

    @Override
    public Message setUnread(boolean unread) {
        return null;
    }

    @Override
    public Message setTopic(String topic) {
        return null;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", message='" + message + '\'' +
                ", topic='" + topic + '\'' +
                ", unread=" + unread +
                '}';
    }
}