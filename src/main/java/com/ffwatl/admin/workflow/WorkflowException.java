package com.ffwatl.admin.workflow;

import com.ffwatl.common.exception.BaseException;

/**
 * @author ffw_ATL.
 */
public class WorkflowException extends BaseException {

    private static final long serialVersionUID = 1L;

    public WorkflowException() {
        super();
    }

    public WorkflowException(String message, Throwable cause) {
        super(message, cause);
    }

    public WorkflowException(String message) {
        super(message);
    }

    public WorkflowException(Throwable cause) {
        super(cause);
    }
}
