package com.ffwatl.admin.user.domain;



public interface OperatorCode {
    long getId();
    int getCode();

    OperatorCode setId(long id);
    OperatorCode setCode(int code);
}