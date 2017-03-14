package com.ffwatl.admin.workflow;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ffw_ATL.
 */
public class DefaultErrorHandler implements ErrorHandler {

    private static final Logger logger = LogManager.getLogger();
    private List<String> unloggedExceptionClasses = new ArrayList<>();

    @Override
    public void handleError(ProcessContext context, Throwable th) throws WorkflowException {
        context.stopProcess();

        boolean shouldLog = true;
        Throwable cause = th;

        while (true) {
            if (unloggedExceptionClasses.contains(cause.getClass().getName())) {
                shouldLog = false;
                break;
            }
            cause = cause.getCause();
            if (cause == null) {
                break;
            }
        }
        if (shouldLog) {
            logger.error("An error occurred during the workflow", th);
        }

        throw new WorkflowException(th);
    }

    public List<String> getUnloggedExceptionClasses() {
        return unloggedExceptionClasses;
    }

    public void setUnloggedExceptionClasses(List<String> unloggedExceptionClasses) {
        this.unloggedExceptionClasses = unloggedExceptionClasses;
    }
}