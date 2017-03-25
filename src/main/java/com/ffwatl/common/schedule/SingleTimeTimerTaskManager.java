package com.ffwatl.common.schedule;

/**
 * @author ffw_ATL.
 */
public interface SingleTimeTimerTaskManager {

    void addTask(Long key, SingleTimeTimerTask task);

    SingleTimeTimerTask removeTask(Long key);

    void cancelTask(Long key);

    void updateTask(Long key, SingleTimeTimerTask task);
}
