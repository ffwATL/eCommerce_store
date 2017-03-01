package com.ffwatl.admin.workflow;

import java.util.List;

/**
 * @author ffw_ATL.
 */
public interface Processor {

    boolean supports(Activity<? extends ProcessContext<?>> activity);

    ProcessContext<?> doActivities() throws WorkflowException;

    ProcessContext<?> doActivities(Object seedData) throws WorkflowException;

    void setActivities(List<Activity<ProcessContext<?>>> activities);

    void setDefaultErrorHandler(ErrorHandler defaultErrorHandler);

    void setProcessContextFactory(ProcessContextFactory<Object, Object> processContextFactory);
}