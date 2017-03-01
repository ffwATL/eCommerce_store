package com.ffwatl.admin.workflow;

/**
 * @author ffw_ATL.
 */
public interface ProcessContextFactory<U, T> {

    ProcessContext<U> createContext(T preSeedData) throws WorkflowException;
}
