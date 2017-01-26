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
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @OneToMany(cascade = CascadeType.REFRESH, targetEntity = UserImpl.class)
    @JoinColumn(name = "to_user_id")
    private List<User> toUsers;

    @Column(name = "message")
    private String message;

    @Column(name = "topic")
    private String topic;

    @Column(name = "unread")
    private boolean unread = true;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public User getFromUser() {
        return fromUser;
    }

    @Override
    public boolean isUnread() {
        return unread;
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
    public Message setToUsers(List<User> toUsers) {
        this.toUsers = toUsers;
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

        MessageImpl message1 = (MessageImpl) o;

        if (getId() != message1.getId()) return false;
        if (isUnread() != message1.isUnread()) return false;
        if (getFromUser() != null ? !getFromUser().equals(message1.getFromUser()) : message1.getFromUser() != null)
            return false;
        if (getToUsers() != null ? !getToUsers().equals(message1.getToUsers()) : message1.getToUsers() != null)
            return false;
        if (getMessage() != null ? !getMessage().equals(message1.getMessage()) : message1.getMessage() != null)
            return false;
        return !(getTopic() != null ? !getTopic().equals(message1.getTopic()) : message1.getTopic() != null);

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
        return "MessageImpl{" +
                "id=" + id +
                ", from=" + fromUser +
                ", to=" + toUsers +
                ", message='" + message + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }
}