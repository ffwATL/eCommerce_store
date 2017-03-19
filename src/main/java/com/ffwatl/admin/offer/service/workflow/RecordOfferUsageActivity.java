package com.ffwatl.admin.offer.service.workflow;

import com.ffwatl.admin.checkout.workflow.CheckoutSeed;
import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.offer.domain.OfferAudit;
import com.ffwatl.admin.offer.domain.OfferCode;
import com.ffwatl.admin.offer.service.OfferAuditService;
import com.ffwatl.admin.offer.service.OfferService;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;
import com.ffwatl.admin.workflow.state.ActivityStateManagerImpl;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author ffw_ATL.
 */
public class RecordOfferUsageActivity extends BaseActivity<ProcessContext<CheckoutSeed>> {

    /**
     * Key to retrieve the audits that were persisted
     */
    public static final String SAVED_AUDITS = "savedAudits";

    @Resource(name = "offer_audit_service")
    private OfferAuditService offerAuditService;

    @Resource(name = "offer_service")
    private OfferService offerService;

    @Override
    public ProcessContext<CheckoutSeed> execute(ProcessContext<CheckoutSeed> context) throws Exception {
        Order order = context.getSeedData().getOrder();
        Set<Offer> appliedOffers = offerService.getUniqueOffersFromOrder(order);
        Map<Offer, OfferCode> offerToCodeMapping = offerService.getOfferRetrievedFromCode(order.getOfferCode(), appliedOffers);

        List<OfferAudit> audits = saveOfferIds(appliedOffers, offerToCodeMapping, order);

        Map<String, Object> state = new HashMap<>();
        state.put(SAVED_AUDITS, audits);

        ActivityStateManagerImpl.getStateManager().registerState(this, context, getRollbackHandler(), state);

        return context;
    }

    /**
     * Persists each of the offers to the database as {@link OfferAudit}s.
     *
     * @return the {@link OfferAudit}s that were persisted
     */
    protected List<OfferAudit> saveOfferIds(Set<Offer> offers, Map<Offer, OfferCode> offerToCodeMapping, Order order) {
        List<OfferAudit> audits = new ArrayList<>(offers.size());
        for (Offer offer : offers) {
            OfferAudit audit = offerAuditService.create();
            audit.setCustomerId(order.getCustomer().getId());
            audit.setOfferId(offer.getId());
            audit.setOrderId(order.getId());

            //add the code that was used to obtain the offer to the audit context
            OfferCode codeUsedToRetrieveOffer = offerToCodeMapping.get(offer);
            if (codeUsedToRetrieveOffer != null) {
                audit.setOfferCodeId(codeUsedToRetrieveOffer.getId());
            }

            audit.setRedeemedDate(new Date());
            audit = offerAuditService.save(audit);
            audits.add(audit);
        }

        return audits;
    }
}