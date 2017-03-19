package com.ffwatl.admin.offer.service.exception;

import com.ffwatl.admin.checkout.exception.CheckoutException;
import com.ffwatl.admin.checkout.workflow.CheckoutSeed;

/**
 * @author ffw_ATL.
 */
public class OfferException extends CheckoutException {

    private static final long serialVersionUID = 1L;

    public OfferException() {
        super();
    }

    public OfferException(String message) {
        super(message, null);
    }

    public OfferException(String message, Throwable cause, CheckoutSeed seed) {
        super(message, cause, seed);
    }

    public OfferException(String message, CheckoutSeed seed) {
        super(message, seed);
    }

    public OfferException(Throwable cause, CheckoutSeed seed) {
        super(cause, seed);
    }
}
