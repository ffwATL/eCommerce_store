package com.ffwatl.admin.user.domain;


import javax.persistence.*;

@Entity
@Table(name = "op_code")
public class OperatorCodeImpl implements OperatorCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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
    public String toString() {
        return "OperatorCodeImpl{" +
                "id=" + id +
                ", code=" + code +
                '}';
    }
}