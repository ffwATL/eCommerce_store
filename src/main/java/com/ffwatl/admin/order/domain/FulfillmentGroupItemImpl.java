package com.ffwatl.admin.order.domain;

import com.ffwatl.admin.order.service.FulfillmentGroupStatusType;

import javax.persistence.*;

@Entity
@Table(name = "fulfillment_group_items")
@NamedQueries({
        @NamedQuery(name = "find_fulfillment_group_items_for_fulfillment_group",
                query = "SELECT f FROM FulfillmentGroupItemImpl f WHERE f.fulfillmentGroup.id=:id")
})
public class FulfillmentGroupItemImpl implements FulfillmentGroupItem {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = FulfillmentGroupImpl.class, optional = false)
    @JoinColumn(name = "fulfillment_group_id")
    private FulfillmentGroup fulfillmentGroup;

    @ManyToOne(targetEntity = OrderItemImpl.class)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "status")
    private String status;

    @Column(name = "retail_price")
    private int retailPrice;

    @Column(name = "total_item_amount")
    private int totalItemAmount;

    @Column(name = "prorated_order_adj")
    private int proratedOrderAdjustment;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public FulfillmentGroup getFulfillmentGroup() {
        return fulfillmentGroup;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public int getRetailPrice() {
        return retailPrice;
    }

    @Override
    public int getTotalItemAmount() {
        return totalItemAmount;
    }

    @Override
    public int getProratedOrderAdjustmentAmount() {
        return proratedOrderAdjustment;
    }

    @Override
    public FulfillmentGroupStatusType getStatus() {
        return FulfillmentGroupStatusType.getInstance(status);
    }

    @Override
    public OrderItem getOrderItem() {
        return orderItem;
    }

    @Override
    public FulfillmentGroupItem setId(long id) {
        this.id= id;
        return this;
    }

    @Override
    public FulfillmentGroupItem setFulfillmentGroup(FulfillmentGroup fulfillmentGroup) {
        this.fulfillmentGroup = fulfillmentGroup;
        return this;
    }

    @Override
    public FulfillmentGroupItem setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public FulfillmentGroupItem setRetailPrice(int retailPrice) {
        this.retailPrice = retailPrice;
        return this;
    }

    @Override
    public FulfillmentGroupItem setTotalItemAmount(int totalItemAmount) {
        this.totalItemAmount = totalItemAmount;
        return this;
    }

    @Override
    public FulfillmentGroupItem setProratedOrderAdjustmentAmount(int proratedOrderAdjustmentAmount) {
        this.proratedOrderAdjustment = proratedOrderAdjustmentAmount;
        return this;
    }

    @Override
    public FulfillmentGroupItem setStatus(FulfillmentGroupStatusType status) {
        if(status!= null) this.status = status.getType();
        return this;
    }

    @Override
    public FulfillmentGroupItem setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FulfillmentGroupItemImpl that = (FulfillmentGroupItemImpl) o;

        if (getId() != that.getId()) return false;
        if (getQuantity() != that.getQuantity()) return false;
        if (getRetailPrice() != that.getRetailPrice()) return false;
        if (getTotalItemAmount() != that.getTotalItemAmount()) return false;
        if (proratedOrderAdjustment != that.proratedOrderAdjustment) return false;
        if (getFulfillmentGroup() != null ? !getFulfillmentGroup().equals(that.getFulfillmentGroup()) : that.getFulfillmentGroup() != null)
            return false;
        return !(getStatus() != null ? !getStatus().equals(that.getStatus()) : that.getStatus() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getFulfillmentGroup() != null ? getFulfillmentGroup().hashCode() : 0);
        result = 31 * result + getQuantity();
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        result = 31 * result + getRetailPrice();
        result = 31 * result + getTotalItemAmount();
        result = 31 * result + proratedOrderAdjustment;
        return result;
    }

    @Override
    public String toString() {
        return "FulfillmentGroupItemImpl{" +
                "id=" + id +
                ", fulfillmentGroup=" + fulfillmentGroup +
                ", quantity=" + quantity +
                ", status='" + status + '\'' +
                ", retailPrice=" + retailPrice +
                ", totalItemAmount=" + totalItemAmount +
                ", proratedOrderAdjustment=" + proratedOrderAdjustment +
                '}';
    }
}