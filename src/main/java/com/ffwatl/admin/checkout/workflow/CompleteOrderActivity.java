package com.ffwatl.admin.checkout.workflow;

import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderStatus;
import com.ffwatl.admin.order.service.OrderNumberGenerator;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;
import com.ffwatl.common.event.OrderSubmittedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

/**
 * @author ffw_ATL.
 */
public class CompleteOrderActivity extends BaseActivity<ProcessContext<CheckoutSeed>> {

    @Autowired
    private ApplicationContext applicationContext;

    @Resource(name = "orderNumberGenerator")
    private OrderNumberGenerator orderNumberGenerator;

    public CompleteOrderActivity() {
        //no specific state to set here for the rollback handler; it's always safe for it to run
        setAutomaticallyRegisterRollbackHandler(true);
    }

    @Override
    public ProcessContext<CheckoutSeed> execute(ProcessContext<CheckoutSeed> context) throws Exception {
        CheckoutSeed seed = context.getSeedData();
        Order order = seed.getOrder();

        order.setOrderStatus(getCompletedStatus());
        order.setOrderNumber(determineOrderNumber());
        order.setSubmitDate(determineSubmitDate());

        OrderSubmittedEvent event = new OrderSubmittedEvent(order.getId(), order.getOrderNumber());
        applicationContext.publishEvent(event);

        return context;
    }

    private Date determineSubmitDate() {
        return Calendar.getInstance().getTime();
    }

    private String determineOrderNumber() {
        return String.valueOf(orderNumberGenerator.getCounter());
    }

    private OrderStatus getCompletedStatus() {
        return OrderStatus.SUBMITTED;
    }
}