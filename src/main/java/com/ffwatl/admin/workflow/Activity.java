package com.ffwatl.admin.workflow;

import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.workflow.state.RollbackHandler;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.core.Ordered;

import java.util.Map;

/**
 * <p>
 * Interface to be used for workflows in Broadleaf. Usually implementations will subclass {@link BaseActivity}.
 * </p>
 *
 * @param <T>
 * @author ffw_ATL.
 */
public interface Activity <T extends ProcessContext<?>> extends BeanNameAware, Ordered {

    /**
     * Called by the encompassing processor to activate
     * the execution of the Activity
     *
     * @param context - process context for this workflow
     * @return resulting process context
     * @throws Exception
     */
    T execute(T context) throws Exception;

    /**
     * Determines if an activity should execute based on the current values in the {@link ProcessContext}. For example, a
     * context might have both an {@link Order} as well as a String 'status' of what the order should be changed to. It is
     * possible that an activity in a workflow could only deal with a particular status change, and thus could return false
     * from this method.
     */
    boolean shouldExecute(T context);

    /**
     * Get the fine-grained error handler wired up for this Activity
     */
    ErrorHandler getErrorHandler();

    void setErrorHandler(final ErrorHandler errorHandler);

    /**
     * Retrieve the RollbackHandler instance that should be called by the ActivityStateManager in the
     * event of a workflow execution problem. This RollbackHandler will presumably perform some
     * compensating operation to revert state for the activity.
     *
     * @return the handler responsible for reverting state for the activity
     */
    RollbackHandler<T> getRollbackHandler();

    /**
     * Set the RollbackHandler instance that should be called by the ActivityStateManager in the
     * event of a workflow execution problem. This RollbackHandler will presumably perform some
     * compensating operation to revert state for the activity.
     *
     * @param rollbackHandler the handler responsible for reverting state for the activity
     */
    void setRollbackHandler(RollbackHandler<T> rollbackHandler);

    /**
     * Retrieve the optional region label for the RollbackHandler. Setting a region allows
     * partitioning of groups of RollbackHandlers for more fine grained control of rollback behavior.
     * Explicit calls to the ActivityStateManager API in an ErrorHandler instance allows explicit rollback
     * of specific rollback handler regions. Note, to disable automatic rollback behavior and enable explicit
     * rollbacks via the API, the workflow.auto.rollback.on.error property should be set to false
     * in your implementation's runtime property configuration.
     *
     * @return the rollback region label for the RollbackHandler instance
     */
    String getRollbackRegion();

    /**
     * Set the optional region label for the RollbackHandler. Setting a region allows
     * partitioning of groups of RollbackHandlers for more fine grained control of rollback behavior.
     * Explicit calls to the ActivityStateManager API in an ErrorHandler instance allows explicit rollback
     * of specific rollback handler regions. Note, to disable automatic rollback behavior and enable explicit
     * rollbacks via the API, the workflow.auto.rollback.on.error property should be set to false
     * in your implementation's runtime property configuration.
     *
     * @param rollbackRegion the rollback region label for the RollbackHandler instance
     */
    void setRollbackRegion(String rollbackRegion);

    /**
     * Retrieve any user-defined state that should accompany the RollbackHandler. This configuration will be passed to
     * the RollbackHandler implementation at runtime.
     *
     * @return any user-defined state configuratio necessary for the execution of the RollbackHandler
     */
    Map<String, Object> getStateConfiguration();

    /**
     * Set any user-defined state that should accompany the RollbackHandler. This configuration will be passed to
     * the RollbackHandler implementation at runtime.
     *
     * @param stateConfiguration any user-defined state configuration necessary for the execution of the RollbackHandler
     */
    void setStateConfiguration(Map<String, Object> stateConfiguration);


    /**
     * Whether or not this activity should automatically register a configured RollbackHandler with the ActivityStateManager.
     * It is useful to adjust this value if you plan on using the ActivityStateManager API to register RollbackHandlers
     * explicitly in your code. The default value is false.
     *
     * @return Whether or not to automatically register a RollbackHandler with the ActivityStateManager
     */
    boolean getAutomaticallyRegisterRollbackHandler();

    /**
     * Whether or not this activity should automatically register a configured RollbackHandler with
     * the ActivityStateManager. It is useful to adjust this value if you plan on using the ActivityStateManager API
     * to register RollbackHandlers explicitly in your code. The default value is false.
     *
     * @param automaticallyRegisterRollbackHandler Whether or not to automatically register a RollbackHandler with the ActivityStateManager
     */
    void setAutomaticallyRegisterRollbackHandler(boolean automaticallyRegisterRollbackHandler);

    String getBeanName();
}
