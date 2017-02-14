package com.ffwatl.admin.order.domain;

import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.offer.domain.OrderItemPriceDetailAdjustment;
import com.ffwatl.admin.offer.domain.OrderItemPriceDetailAdjustmentImpl;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "order_item_price_details")
public class OrderItemPriceDetailImpl implements OrderItemPriceDetail{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = OrderItemImpl.class)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    @OneToMany(mappedBy = "orderItemPriceDetail",
               fetch = FetchType.LAZY,
               targetEntity = OrderItemPriceDetailAdjustmentImpl.class,
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private Set<OrderItemPriceDetailAdjustment> orderItemPriceDetailAdjustments = new HashSet<>();

    @Column(name = "quantity")
    private int quantity;


    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public OrderItem getOrderItem() {
        return this.orderItem;
    }

    @Override
    public Set<OrderItemPriceDetailAdjustment> getOrderItemPriceDetailAdjustments() {
        return this.orderItemPriceDetailAdjustments;
    }

    @Override
    public int getQuantity() {
        return this.quantity;
    }

    @Override
    public int getAdjustmentValue() {
        int adjustmentValue = 0;
        for(OrderItemPriceDetailAdjustment adjustment: orderItemPriceDetailAdjustments){
            adjustmentValue += adjustment.getAdjustmentValue();
        }
        return adjustmentValue;
    }

    @Override
    public int getTotalAdjustmentValue() {
        return getAdjustmentValue() * quantity;
    }

    @Override
    public int getTotalAdjustedPrice() {
        int basePrice = orderItem.getTotalPriceBeforeAdjustments();
        return (basePrice * quantity) - getAdjustmentValue();
    }

    @Override
    public Currency getCurrency() {
        OrderItem orderItem = getOrderItem();
        return orderItem != null && orderItem.getOrder() != null
                ? orderItem.getOrder().getCurrency()
                : null;
    }

    @Override
    public OrderItemPriceDetail setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public OrderItemPriceDetail setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
        return this;
    }

    @Override
    public OrderItemPriceDetail setOrderItemPriceDetailAdjustments(Set<OrderItemPriceDetailAdjustment> orderItemPriceDetailAdjustments) {
        this.orderItemPriceDetailAdjustments = orderItemPriceDetailAdjustments;
        return this;
    }

    @Override
    public OrderItemPriceDetail setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItemPriceDetailImpl that = (OrderItemPriceDetailImpl) o;

        if (getId() != that.getId()) return false;
        if (getQuantity() != that.getQuantity()) return false;
        if (getOrderItem() != null ? !getOrderItem().equals(that.getOrderItem()) : that.getOrderItem() != null)
            return false;
        return !(getOrderItemPriceDetailAdjustments() != null ? !getOrderItemPriceDetailAdjustments().equals(that.getOrderItemPriceDetailAdjustments()) : that.getOrderItemPriceDetailAdjustments() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getOrderItem() != null ? getOrderItem().hashCode() : 0);
        result = 31 * result + (getOrderItemPriceDetailAdjustments() != null ? getOrderItemPriceDetailAdjustments().hashCode() : 0);
        result = 31 * result + getQuantity();
        return result;
    }

    @Override
    public String toString() {
        return "OrderItemPriceDetailImpl{" +
                "id=" + id +
                ", orderItem=" + orderItem +
                ", orderItemPriceDetailAdjustments=" + orderItemPriceDetailAdjustments +
                ", quantity=" + quantity +
                '}';
    }
}