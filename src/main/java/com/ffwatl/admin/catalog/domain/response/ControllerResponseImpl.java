package com.ffwatl.admin.catalog.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.beans.Transient;

/**
 * @author mmed 11/15/17
 */
public class ControllerResponseImpl implements ControllerResponse {


    private static final long serialVersionUID = -8506127243837768664L;

    @JsonIgnore
    public transient static final ControllerResponse OK = new ControllerResponseImpl().setMessage("OK!");

    private Exception exception;
    private String message = "";

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public ControllerResponse setException(Exception e) {
        this.exception = e;
        return this;
    }

    @Override
    public ControllerResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "ControllerResponseImpl{" +
                "exception=" + exception +
                ", message='" + message + '\'' +
                '}';
    }
}