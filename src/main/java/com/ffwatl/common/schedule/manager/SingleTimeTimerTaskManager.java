package com.ffwatl.common.schedule.manager;

import com.ffwatl.common.schedule.service.SingleTimeTimerTask;

/**
 * @author ffw_ATL.
 */
public interface SingleTimeTimerTaskManager {

    SingleTimeTimerTask addTask(Long key, SingleTimeTimerTask task);

    SingleTimeTimerTask removeTask(Long key);

    void cancelTask(SingleTimeTimerTask task);

    SingleTimeTimerTask removeAndCancelTaskIfExist(Long key);

    int getPendingTasksSize();

    void clearAllTasks();
}
