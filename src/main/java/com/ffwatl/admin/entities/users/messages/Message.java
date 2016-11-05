package com.ffwatl.admin.entities.users.messages;


import com.ffwatl.admin.entities.users.IUser;

import java.util.List;

public interface Message {

    long getId();
    String getTopic();
    IUser getFrom();
    boolean isUnread();
    List<IUser> getTo();
    String getMessage();

    Message setId(long id);
    Message setFrom(IUser from);
    Message setTo(List<IUser> to);
    Message setMessage(String message);
    Message setUnread(boolean unread);
    Message setTopic(String topic);
}