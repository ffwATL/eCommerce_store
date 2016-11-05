package com.ffwatl.admin.entities.users.messages;


import com.ffwatl.admin.entities.users.IUser;
import com.ffwatl.admin.entities.users.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "p_messages")
public class PrivateMessage implements Message{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.REFRESH, targetEntity = User.class)
    private IUser from;

    @OneToMany(cascade = CascadeType.REFRESH, targetEntity = User.class)
    private List<IUser> to;

    private String message;

    private String topic;

    private boolean unread = true;

    public long getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public IUser getFrom() {
        return from;
    }

    public boolean isUnread() {
        return unread;
    }

    public List<IUser> getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    public Message setId(long id) {
        this.id = id;
        return this;
    }

    public Message setFrom(IUser from) {
        this.from = from;
        return this;
    }

    public Message setTo(List<IUser> to) {
        this.to = to;
        return this;
    }

    public Message setMessage(String message) {
        this.message = message;
        return this;
    }

    public Message setUnread(boolean unread) {
        this.unread = unread;
        return this;
    }

    public Message setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    @Override
    public String toString() {
        return "PrivateMessage{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", message='" + message + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }
}