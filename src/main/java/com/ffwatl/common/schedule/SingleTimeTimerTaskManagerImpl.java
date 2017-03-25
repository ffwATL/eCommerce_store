package com.ffwatl.common.schedule;

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

    private Map<Long, SingleTimeTimerTask> taskMap = new ConcurrentHashMap<>();

    @Override
    public void addTask(Long key, SingleTimeTimerTask task) {
        SingleTimeTimerTask prev = taskMap.putIfAbsent(key, task);

        if(prev != null) {
            throw new IllegalArgumentException("Task is already added. Please remove the task firstly and retry.");
        }

        scheduleTimerTask(task);
    }

    @Override
    public SingleTimeTimerTask removeTask(Long key) {
        return taskMap.remove(key);
    }

    private void scheduleTimerTask(SingleTimeTimerTask task) {
       /* ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
        scheduler.schedule(task, Date.from(task.getStartDateTime().atZone(ZoneId.systemDefault()).toInstant()));*/
        Timer timer = new Timer();
        timer.schedule(task, Date.from(task.getStartDateTime().atZone(ZoneId.systemDefault()).toInstant()));
        task.setTimer(timer);
    }

    @Override
    public void cancelTask(Long key) {
        SingleTimeTimerTask task = removeTask(key);
        if(task != null) {
            task.getTimer().cancel();
            task.cancel();
        }
    }

    @Override
    public void updateTask(Long key, SingleTimeTimerTask task) {
        cancelTask(key);
        addTask(key, task);
    }

}