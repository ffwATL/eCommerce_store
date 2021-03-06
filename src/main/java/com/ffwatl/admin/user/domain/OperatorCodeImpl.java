package com.ffwatl.admin.user.domain;


import javax.persistence.*;

@Entity
@Table(name = "op_code")
public class OperatorCodeImpl implements OperatorCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "code")
    private int code;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public OperatorCode setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public OperatorCode setCode(int code) {
        this.code = code;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OperatorCodeImpl that = (OperatorCodeImpl) o;

        if (getId() != that.getId()) return false;
        return getCode() == that.getCode();

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getCode();
        return result;
    }

    @Override
    public String toString() {
        return "OperatorCodeImpl{" +
                "id=" + id +
                ", code=" + code +
                '}';
    }
}