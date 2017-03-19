package com.ffwatl.admin.checkout.exception;

import com.ffwatl.admin.checkout.workflow.CheckoutResponse;
import com.ffwatl.admin.checkout.workflow.CheckoutSeed;
import com.ffwatl.common.exception.BaseException;

/**
 * @author ffw_ATL.
 */
public class CheckoutException extends BaseException {

    private CheckoutResponse checkoutResponse;

    public CheckoutException() {
        super();
    }

    public CheckoutException(String message, CheckoutSeed seed) {
        super(message);
        checkoutResponse = seed;
    }

    public CheckoutException(Throwable cause, CheckoutSeed seed) {
        super(cause);
        checkoutResponse = seed;
    }

    public CheckoutException(String message, Throwable cause, CheckoutSeed seed) {
        super(message, cause);
        checkoutResponse = seed;
    }

    public CheckoutResponse getCheckoutResponse() {
        return checkoutResponse;
    }

    public void setCheckoutResponse(CheckoutResponse checkoutResponse) {
        this.checkoutResponse = checkoutResponse;
    }
}