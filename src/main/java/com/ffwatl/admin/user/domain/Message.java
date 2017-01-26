package com.ffwatl.admin.user.domain;


import java.util.List;

public interface Message {

    long getId();
    String getTopic();
    User getFromUser();
    boolean isUnread();
    List<User> getToUsers();
    String getMessage();

    Message setId(long id);
    Message setFromUsers(User fromUser);
    Message setToUsers(List<User> toUsers);
    Message setMessage(String message);
    Message setUnread(boolean unread);
    Message setTopic(String topic);
}