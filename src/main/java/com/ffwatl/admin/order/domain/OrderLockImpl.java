package com.ffwatl.admin.order.domain;


import javax.persistence.*;

@Entity
@Table(name = "order_lock")
public class OrderLockImpl implements OrderLock{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "locked")
    private boolean locked;

    @Column(name = "last_updated")
    private long lastUpdated;

    @Column(name = "node_key")
    private String key;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public boolean istLocked() {
        return locked;
    }

    @Override
    public long getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public OrderLock setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public OrderLock setLocked(boolean locked) {
        this.locked = locked;
        return this;
    }

    @Override
    public OrderLock setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    @Override
    public OrderLock setKey(String key) {
        this.key = key;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderLockImpl orderLock = (OrderLockImpl) o;

        if (getId() != orderLock.getId()) return false;
        if (locked != orderLock.locked) return false;
        if (getLastUpdated() != orderLock.getLastUpdated()) return false;
        return !(getKey() != null ? !getKey().equals(orderLock.getKey()) : orderLock.getKey() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (locked ? 1 : 0);
        result = 31 * result + (int) (getLastUpdated() ^ (getLastUpdated() >>> 32));
        result = 31 * result + (getKey() != null ? getKey().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderLockImpl{" +
                "id=" + id +
                ", locked=" + locked +
                ", lastUpdated=" + lastUpdated +
                ", key='" + key + '\'' +
                '}';
    }
}