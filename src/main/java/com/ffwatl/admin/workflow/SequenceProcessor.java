package com.ffwatl.admin.workflow;

import com.ffwatl.admin.workflow.state.ActivityStateManager;
import com.ffwatl.admin.workflow.state.ActivityStateManagerImpl;
import com.ffwatl.admin.workflow.state.RollbackStateLocal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ffw_ATL.
 */
public class SequenceProcessor extends BaseProcessor {

    private static final Logger LOGGER = LogManager.getLogger();
    private ProcessContextFactory<Object, Object> processContextFactory;

    @Resource(name = "activity_state_manager")
    private ActivityStateManager manager;

    @Override
    public boolean supports(Activity<? extends ProcessContext<?>> activity) {
        return true;
    }

    @Override
    public ProcessContext<?> doActivities() throws WorkflowException {
        return doActivities(null);
    }

    @Override

    public ProcessContext<?> doActivities(Object seedData) throws WorkflowException {
        LOGGER.debug(getBeanName() + " processor is running..");

        ProcessContext<?> context;

        RollbackStateLocal rollbackStateLocal = new RollbackStateLocal();
        rollbackStateLocal.setThreadId(String.valueOf(Thread.currentThread().getId()));
        rollbackStateLocal.setWorkflowId(getBeanName());
        RollbackStateLocal.setRollbackStateLocal(rollbackStateLocal);

        try {
            //retrieve injected by Spring
            List<Activity<ProcessContext<?>>> activities = getActivities();

            //retrieve a new instance of the Workflow ProcessContext
            context = createContext(seedData);

            for (Activity<ProcessContext<?>> activity : activities) {
                if (activity.shouldExecute(context)) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("running activity:" + activity.getBeanName() + " using arguments:" + context);
                    }

                    try {
                        context = activity.execute(context);
                    } catch (Throwable th) {
                        try {
                            if (getAutoRollbackOnError()) {
                                LOGGER.info("Automatically rolling back state for any previously registered " +
                                        "RollbackHandlers. RollbackHandlers may be registered for workflow activities" +
                                        " in appContext.");
                                ActivityStateManagerImpl.getStateManager().rollbackAllState();
                            }
                            ErrorHandler errorHandler = activity.getErrorHandler();
                            if (errorHandler == null) {
                                LOGGER.info("no error handler for this action, run default error" + "handler and abort " +
                                        "processing ");
                                getDefaultErrorHandler().handleError(context, th);
                                break;
                            } else {
                                LOGGER.info("run error handler and continue");
                                errorHandler.handleError(context, th);
                            }
                        } catch (RuntimeException | WorkflowException e) {
                            LOGGER.error("An exception was caught while attempting to handle an activity generated exception", e);
                            throw e;
                        }
                    }

                    //ensure its ok to continue the process
                    if (processShouldStop(context, activity)) {
                        break;
                    }

                    //register the RollbackHandler
                    if (activity.getRollbackHandler() != null && activity.getAutomaticallyRegisterRollbackHandler()) {
                        ActivityStateManagerImpl.getStateManager().registerState(activity, context,
                                activity.getRollbackRegion(), activity.getRollbackHandler(), activity.getStateConfiguration());
                    }
                } else {
                    LOGGER.debug("Not executing activity: " + activity.getBeanName() + " based on the context: " + context);
                }
            }
        } finally {
            rollbackStateLocal = RollbackStateLocal.getRollbackStateLocal();
            if (rollbackStateLocal != null && rollbackStateLocal.getWorkflowId().equals(getBeanName())) {
                manager.clearAllState();
            }
        }
        return context;
    }

    /**
     * Determine if the process should stop
     *
     * @param context  the current process context
     * @param activity the current activity in the iteration
     */
    protected boolean processShouldStop(ProcessContext<?> context, Activity<? extends ProcessContext<?>> activity) {
        if (context == null || context.isStopped()) {
            LOGGER.info("Interrupted workflow as requested by:" + activity.getBeanName());
            return true;
        }
        return false;
    }

    protected ProcessContext<Object> createContext(Object seedData) throws WorkflowException {
        return processContextFactory.createContext(seedData);
    }

    @Override
    public void setProcessContextFactory(ProcessContextFactory<Object, Object> processContextFactory) {
        this.processContextFactory = processContextFactory;
    }
}
