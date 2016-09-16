package com.ffwatl.domain.users.messages;


import com.ffwatl.domain.users.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "p_messages")
public class PrivateMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.REFRESH)
    private User from;

    @OneToMany(cascade = CascadeType.REFRESH)
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

    public void setId(long id) {
        this.id = id;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public void setTo(List<User> to) {
        this.to = to;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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