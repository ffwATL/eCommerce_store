package com.ffwatl.admin.offer.service.workflow;

import com.ffwatl.admin.checkout.workflow.CheckoutSeed;
import com.ffwatl.admin.offer.domain.OfferAudit;
import com.ffwatl.admin.offer.service.OfferAuditService;
import com.ffwatl.admin.workflow.Activity;
import com.ffwatl.admin.workflow.ProcessContext;
import com.ffwatl.admin.workflow.state.RollbackFailureException;
import com.ffwatl.admin.workflow.state.RollbackHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author ffw_ATL.
 */
@Component("record_offer_usage_rollback_handler")
public class RecordOfferUsageRollbackHandler implements RollbackHandler<CheckoutSeed> {

    @Resource(name = "offer_audit_service")
    private OfferAuditService offerAuditService;

    @Override
    @SuppressWarnings("unchecked")
    public void rollbackState(Activity<? extends ProcessContext<CheckoutSeed>> activity,
                              ProcessContext<CheckoutSeed> processContext,
                              Map<String, Object> stateConfiguration) throws RollbackFailureException {

        List<OfferAudit> audits = (List<OfferAudit>) stateConfiguration.get(RecordOfferUsageActivity.SAVED_AUDITS);

        for (OfferAudit audit : audits) {
            offerAuditService.delete(audit);
        }
    }
}