package com.ffwatl.admin.user.domain.dto;

import com.ffwatl.admin.user.domain.OperatorCode;


public class OperatorCodeDTO implements OperatorCode {

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
        return "OperatorCodeDTO{" +
                "id=" + id +
                ", code=" + code +
                '}';
    }
}