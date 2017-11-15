package com.ffwatl.admin.catalog.domain.response;

import java.io.Serializable;

/**
 * @author mmed 11/15/17
 */
public interface ControllerResponse extends Serializable {

    Exception getException();

    String getMessage();

    ControllerResponse setException(Exception e);

    ControllerResponse setMessage(String message);
}
