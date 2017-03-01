package com.ffwatl.admin.workflow.state;

import com.ffwatl.admin.workflow.Activity;
import com.ffwatl.admin.workflow.ProcessContext;

import java.util.Map;

/**
 * Implementations are responsible for performing compensating operations to revert the state of the
 * activity to what it was prior to execution. Activity, ProcessContext and stateConfiguration
 * variables can be used to gather the necessary information to successfully perform the compensating operation.
 *
 * @author ffw_ATL.
 */
public interface RollbackHandler<T> {

    /**
     * Rollback the state of the activity to what it was prior to execution.
     *
     * @param activity The Activity instance whose state is being reverted
     * @param processContext The ProcessContext for the workflow
     * @param stateConfiguration Any user-defined state configuration associated with the RollbackHandler
     * @throws RollbackFailureException if there is a failure during the execution of the rollback
     */
    void rollbackState(Activity<? extends ProcessContext<T>> activity,
                              ProcessContext<T> processContext, Map<String, Object> stateConfiguration) throws RollbackFailureException;
}