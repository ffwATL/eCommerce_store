package com.ffwatl.admin.order.service.exception;

/**
 * @author ffw_ATL.
 */
public class RemoveFromCartException extends Exception {

    private static final long serialVersionUID = 1L;

    public RemoveFromCartException() {
        super();
    }

    public RemoveFromCartException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoveFromCartException(String message) {
        super(message);
    }

    public RemoveFromCartException(Throwable cause) {
        super(cause);
    }
}
