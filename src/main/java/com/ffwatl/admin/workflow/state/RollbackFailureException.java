package com.ffwatl.admin.workflow.state;

import com.ffwatl.admin.workflow.Activity;
import com.ffwatl.admin.workflow.ProcessContext;
import com.ffwatl.admin.workflow.WorkflowException;

import java.util.Map;

/**
 * This exception is thrown to indicate a problem while trying to rollback
 * state for any and all activities during a failed workflow. Only those
 * activities that register their state with the ProcessContext will have
 * their state rolled back.
 *
 * @author ffw_ATL.
 */
public class RollbackFailureException extends WorkflowException {

    private static final long serialVersionUID = 1L;

    private Activity<? extends ProcessContext<?>> activity;
    private ProcessContext<?> processContext;
    private Map<String, Object> stateItems;

    public RollbackFailureException() {
    }

    public RollbackFailureException(Throwable cause) {
        super(cause);
    }

    public RollbackFailureException(String message) {
        super(message);
    }

    public RollbackFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public Activity<? extends ProcessContext<?>> getActivity() {
        return activity;
    }

    public void setActivity(Activity<? extends ProcessContext<?>> activity) {
        this.activity = activity;
    }

    public ProcessContext<?> getProcessContext() {
        return processContext;
    }

    public void setProcessContext(ProcessContext<?> processContext) {
        this.processContext = processContext;
    }

    public Map<String, Object> getStateItems() {
        return stateItems;
    }

    public void setStateItems(Map<String, Object> stateItems) {
        this.stateItems = stateItems;
    }
}
