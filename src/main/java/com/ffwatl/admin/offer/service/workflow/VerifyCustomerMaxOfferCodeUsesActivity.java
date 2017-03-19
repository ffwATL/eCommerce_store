package com.ffwatl.admin.offer.service.workflow;

import com.ffwatl.admin.checkout.workflow.CheckoutSeed;
import com.ffwatl.admin.offer.domain.OfferCode;
import com.ffwatl.admin.offer.service.OfferAuditService;
import com.ffwatl.admin.offer.service.exception.OfferMaxUseExceededException;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;

import javax.annotation.Resource;

/**
 * @author ffw_ATL.
 */
public class VerifyCustomerMaxOfferCodeUsesActivity extends BaseActivity<ProcessContext<CheckoutSeed>> {

    @Resource(name = "offer_audit_service")
    private OfferAuditService offerAuditService;

    @Override
    public ProcessContext<CheckoutSeed> execute(ProcessContext<CheckoutSeed> context) throws Exception {
        Order order = context.getSeedData().getOrder();
        User customer = order.getCustomer();
        OfferCode code = order.getOfferCode();

        long currentCodeUses = offerAuditService.countUsesByCustomer(customer.getId(), code.getId(), code.getVersion());

        if (currentCodeUses >= code.getMaxUses()) {
            throw new OfferMaxUseExceededException("Offer code " + code.getOfferCode() + " with id " + code.getId()
                    + " has been than the maximum allowed number of times.");
        }
        return context;
    }
}