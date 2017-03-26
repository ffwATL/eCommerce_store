package com.ffwatl.common.schedule.manager;

import com.ffwatl.common.schedule.service.SingleTimeTimerTask;

import java.sql.Date;
import java.time.ZoneId;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is for handle single time task scheduling. Tasks are based on {@link TimerTask} instance with
 * predefined startTime. Here user can add, update or cancel tasks using {@link Long} as a key for the {@link #taskMap}
 * It is highly recommended to use entity ID as a key for the {@link #taskMap};
 *
 * Note that for every different key-type (i.e {@link com.ffwatl.admin.order.domain.Order},
 * {@link com.ffwatl.admin.catalog.domain.ProductAttribute} and etc. you should create another prototype
 * of this bean to avoid HashMap collision.
 *
 * @author ffw_ATL.
 */
public class SingleTimeTimerTaskManagerImpl implements SingleTimeTimerTaskManager {

    private volatile Map<Long, SingleTimeTimerTask> taskMap = new ConcurrentHashMap<>();

    @Override
    public SingleTimeTimerTask addTask(Long key, SingleTimeTimerTask task) {
        removeAndCancelTaskIfExist(key);
        return scheduleTimerTask(key, task);
    }

    @Override
    public SingleTimeTimerTask removeTask(Long key) {
        return taskMap.remove(key);
    }

    @Override
    public void cancelTask(SingleTimeTimerTask task) {
        if(task != null) {
            task.getTimer().cancel();
            task.cancel();
        }
    }

    @Override
    public SingleTimeTimerTask removeAndCancelTaskIfExist(Long key){
        SingleTimeTimerTask prev = taskMap.remove(key);
        cancelTask(prev);
        return prev;
    }

    @Override
    public int getPendingTasksSize() {
        return taskMap.size();
    }

    private SingleTimeTimerTask scheduleTimerTask(Long key, SingleTimeTimerTask task) {
        Timer timer = new Timer();
        timer.schedule(task, Date.from(task.getStartDateTime().atZone(ZoneId.systemDefault()).toInstant()));

        task.setTimer(timer);

        SingleTimeTimerTask prev = taskMap.put(key, task);

        if(prev != null) {
            cancelTask(prev);
        }
        return prev;
    }

}