package com.ffwatl.admin.catalog.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * @author mmed 11/15/17
 */
public class ControllerResponse<T> implements Serializable{


    private static final long serialVersionUID = -8506127243837768664L;

    @JsonIgnore
    public transient static final ControllerResponse OK = new ControllerResponse().setMessage("OK!");

    private Exception exception;
    private String message = "";
    private T data;


    public Exception getException() {
        return exception;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public ControllerResponse<T> setException(Exception e) {
        this.exception = e;
        return this;
    }

    public ControllerResponse<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public ControllerResponse<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "ControllerResponse{" +
                "exception=" + exception +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}