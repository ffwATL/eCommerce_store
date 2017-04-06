package com.ffwatl.common.schedule.service;

import com.ffwatl.admin.order.service.OrderService;
import com.ffwatl.common.schedule.manager.SingleTimeTimerTaskManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author ffw_ATL.
 */
@Service("order_single_time_timer_task_service")
public class OrderSingleTimeTimerTaskService implements SingleTimeTimerTaskService {

    private static final Logger logger = LogManager.getLogger();
    private static boolean initialized = false;

    @Resource(name = "orderSingleTimeTimerTaskManager")
    private SingleTimeTimerTaskManager taskManager;

    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    @Resource(name = "order_service")
    private OrderService orderService;


    @PostConstruct
    private void init(){
        if(!initialized) {
            taskExecutor.initialize();
            initialized = true;
        }
    }

    @Override
    public SingleTimeTimerTask createTask(Long key){
        return new SingleTimerTask(key);
    }

    @Override
    public void cancelAndRemoveTask(Long key) {
        taskManager.removeAndCancelTaskIfExist(key);
    }

    @Override
    public SingleTimeTimerTask addTask(SingleTimeTimerTask task) {
        return taskManager.addTask(task.getKey(), task);
    }

    @Override
    public int getPendingTasksSize() {
        return taskManager.getPendingTasksSize();
    }

    @Override
    public void clearAllTasks() {
        taskManager.clearAllTasks();
    }

    public class SingleTimerTask extends SingleTimeTimerTask{

        public SingleTimerTask(Long key) {
            super(key);
        }

        @Override
        public void run() {
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        orderService.deleteOrder(getKey());
                        logger.info("Removed an expired order with id = {}", getKey());
                        taskManager.removeTask(getKey());
                    } catch (Exception e) {
                        logger.error("An error is occurred when tried to delete order with ID {} ", getKey(), e);
                        throw e;
                    }

                }
            });
        }
    }
}