package com.ffwatl.admin.pricing.workflow;

import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.payment.domain.OrderPayment;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;

/**
 * @author ffw_ATL.
 */
public class AdjustOrderPaymentsActivity extends BaseActivity<ProcessContext<Order>> {

    @Override
    public ProcessContext<Order> execute(ProcessContext<Order> context) throws Exception {
        Order order = context.getSeedData();

        OrderPayment unconfirmedThirdPartyOrCreditCard = null;
        int appliedPaymentsWithoutThirdPartyOrCC = 0;
        OrderPayment payment = order.getOrderPayment();

        if (payment.isActive()) {
            if (!payment.isConfirmed())  {
                unconfirmedThirdPartyOrCreditCard = payment;
            } else if (payment.getAmount() != 0) {
                appliedPaymentsWithoutThirdPartyOrCC += payment.getAmount();
            }
        }
        if (unconfirmedThirdPartyOrCreditCard != null) {
            int difference = order.getTotal() - appliedPaymentsWithoutThirdPartyOrCC;
            unconfirmedThirdPartyOrCreditCard.setAmount(difference);
        }

        context.setSeedData(order);
        return context;
    }
}