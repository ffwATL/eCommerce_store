package com.ffwatl.admin.workflow;

/**
 * @author ffw_ATL.
 */
public interface ErrorHandler {

    void handleError(ProcessContext context, Throwable th) throws WorkflowException;
}