package com.ffwatl.admin.user.domain;


import java.util.List;

public interface Message {

    long getId();
    String getTopic();
    User getFrom();
    boolean isUnread();
    List<User> getTo();
    String getMessage();

    Message setId(long id);
    Message setFrom(User from);
    Message setTo(List<User> to);
    Message setMessage(String message);
    Message setUnread(boolean unread);
    Message setTopic(String topic);
}