package com.ffwatl.admin.workflow.state;

import com.ffwatl.common.classloader.ThreadLocalManager;

import java.util.Stack;

/**
 * Handles the identification of the outermost workflow and the current thread so that the StateManager can
 * operate on the appropriate RollbackHandlers.
 *
 * @author ffw_ATL.
 */
public class RollbackStateLocal {

    private static final ThreadLocal<Stack> THREAD_LOCAL = ThreadLocalManager.createThreadLocal(Stack.class);

    public static RollbackStateLocal getRollbackStateLocal() {
        return (RollbackStateLocal) THREAD_LOCAL.get().peek();
    }

    public static void setRollbackStateLocal(RollbackStateLocal rollbackStateLocal) {
        Stack<RollbackStateLocal> localState = THREAD_LOCAL.get();
        localState.push(rollbackStateLocal);
    }

    public static void clearRollbackStateLocal() {
        Stack localState = THREAD_LOCAL.get();
        localState.pop();
    }

    private String threadId;
    private String workflowId;

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }
}