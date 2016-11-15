package com.ffwatl.admin.user.domain;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "p_messages")
public class MessageImpl implements Message{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.REFRESH, targetEntity = UserImpl.class)
    private User from;

    @OneToMany(cascade = CascadeType.REFRESH, targetEntity = UserImpl.class)
    private List<User> to;

    private String message;

    private String topic;

    private boolean unread = true;

    public long getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public User getFrom() {
        return from;
    }

    public boolean isUnread() {
        return unread;
    }

    public List<User> getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    public Message setId(long id) {
        this.id = id;
        return this;
    }

    public Message setFrom(User from) {
        this.from = from;
        return this;
    }

    public Message setTo(List<User> to) {
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
        return "MessageImpl{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", message='" + message + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }
}