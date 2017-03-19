package com.ffwatl.common.event;

/**
 * @author ffw_ATL.
 */
public class OrderSubmittedEvent extends BasicApplicationEvent {

    private static final long serialVersionUID = 1L;

    private final String orderNumber;

    public OrderSubmittedEvent(Long orderId, String orderNumber) {
        super(orderId);
        this.orderNumber = orderNumber;
    }

    public Long getOrderId() {
        return (Long) super.getSource();
    }

    public String getOrderNumber() {
        return (String) orderNumber;
    }
}