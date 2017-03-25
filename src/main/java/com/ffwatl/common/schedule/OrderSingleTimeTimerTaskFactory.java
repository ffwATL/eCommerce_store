package com.ffwatl.common.schedule;

import com.ffwatl.admin.order.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author ffw_ATL.
 */
public class OrderSingleTimeTimerTaskFactory implements SingleTimeTimerTaskFactory {

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
    public SingleTimeTimerTask getInstance(Long key){
        return new SingleTimerTask().setKey(key);
    }

    public class SingleTimerTask extends SingleTimeTimerTask{

        @Override
        public void run() {
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        orderService.deleteOrder(getKey());
                        logger.info("Removed an expired order with id = {}", getKey());
                    } catch (Exception e) {
                        logger.error("An error is occurred when trying to delete order with ID {} ", getKey(), e);
                    } finally {
                        taskManager.removeTask(getKey());
                    }

                }
            });
        }
    }

}