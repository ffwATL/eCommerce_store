package com.ffwatl.common.schedule.service;

/**
 * @author ffw_ATL.
 */
public interface SingleTimeTimerTaskService {

    SingleTimeTimerTask createTask(Long key);

    void cancelAndRemoveTask(Long key);

    SingleTimeTimerTask addTask(SingleTimeTimerTask task);

    int getPendingTasksSize();

    void clearAllTasks();

}