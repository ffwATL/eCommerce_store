package com.ffwatl.admin.order.domain;

import com.ffwatl.admin.catalog.domain.*;
import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.admin.offer.domain.CandidateItemOffer;
import com.ffwatl.admin.offer.domain.CandidateItemOfferImpl;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "order_items")
public class OrderItemImpl implements OrderItem{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "product_id")
    private long productId;

    @ManyToOne(targetEntity = OrderImpl.class)
    @JoinColumn(name = "order_id")
    private Order order;

    @Embedded
    private I18n productName;

    @ManyToOne(targetEntity = ProductAttributeTypeImpl.class)
    @JoinColumn(name = "product_attribute_type_id")
    private ProductAttributeType productAttributeType;

    @ManyToOne(targetEntity = OrderItemColor.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_item_color_id")
    private Color color;

    @Column(name = "origin_price")
    private int originPrice;

    @Column(name = "retail_price")
    private int retailPrice;

    @Column(name = "sale_price")
    private int salePrice;

    @Column(name = "order_item_quantity")
    private int quantity;

    @OneToMany(mappedBy = "orderItem", targetEntity = OrderItemPriceDetailImpl.class,
               fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrderItemPriceDetail> orderItemPriceDetails = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = CategoryImpl.class, optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "orderItem", targetEntity = CandidateItemOfferImpl.class,
               fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CandidateItemOffer> candidateItemOffers = new HashSet<>();

    @OneToMany(mappedBy = "orderItem", targetEntity = OrderItemQualifierImpl.class,
               fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrderItemQualifier> orderItemQualifiers = new HashSet<>();

    @Column(name = "discounting_allowed")
    private boolean discountingAllowed = true;

    @Column(name = "discount_value")
    private int discountValue;


    @Override
    public long getId() {
        return id;
    }

    @Override
    public long getProductId() {
        return productId;
    }

    @Override
    public I18n getProductName() {
        return productName;
    }

    @Override
    public ProductAttributeType getProductAttributeType() {
        return productAttributeType;
    }

    @Override
    public Color getOrderItemColor() {
        return color;
    }

    @Override
    public Order getOrder() {
        return order;
    }

    @Override
    public int getOriginPrice() {
        return originPrice;
    }

    @Override
    public int getRetailPrice() {
        return retailPrice;
    }

    @Override
    public int getSalePrice() {
        return salePrice;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public Set<OrderItemPriceDetail> getOrderItemPriceDetails() {
        return orderItemPriceDetails;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    @Override
    public Set<CandidateItemOffer> getCandidateItemOffers() {
        return candidateItemOffers;
    }

    @Override
    public Set<OrderItemQualifier> getOrderItemQualifiers() {
        return orderItemQualifiers;
    }

    @Override
    public boolean getIsOnSale() {
        return salePrice > 0;
    }

    @Override
    public boolean isDiscountingAllowed() {
        return discountingAllowed;
    }

    @Override
    public boolean getIsDiscounted() {
        return getTotalPrice() < retailPrice;
    }

    @Override
    public int getTotalPrice() {
        int returnValue = 0;

        if(orderItemPriceDetails != null && orderItemPriceDetails.size() > 0){
            for (OrderItemPriceDetail oipd : orderItemPriceDetails) {
                returnValue = returnValue + oipd.getTotalAdjustedPrice();
            }
        }else {
            if(getIsOnSale()){
                returnValue = salePrice * quantity;
            }else {
                return  retailPrice * quantity;
            }
        }
        return returnValue;
    }

    @Override
    public int getDiscountValue() {
        return discountValue;
    }

    @Override
    public int getTotalPriceBeforeAdjustments() {
        return getPriceBeforeAdjustments(getIsOnSale()) * quantity;
    }

    @Override
    public int getPriceBeforeAdjustments(boolean isOnSale) {
        if(isOnSale){
            return salePrice;
        }
        return retailPrice;
    }

    @Override
    public OrderItem setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public OrderItem setProductId(long id) {
        this.productId = id;
        return this;
    }

    @Override
    public OrderItem setProductName(I18n productName) {
        this.productName = productName;
        return this;
    }

    @Override
    public OrderItem setProductAttributeType(ProductAttributeType productAttributeType) {
        this.productAttributeType = productAttributeType;
        return this;
    }

    @Override
    public OrderItem setColor(Color color) {
        this.color = color;
        return this;
    }

    @Override
    public OrderItem setOrder(Order order) {
        this.order = order;
        return this;
    }

    @Override
    public OrderItem setOriginPrice(int originPrice) {
        this.originPrice = originPrice;
        return this;
    }

    @Override
    public OrderItem setRetailPrice(int retailPrice) {
        this.retailPrice = retailPrice;
        return this;
    }

    @Override
    public OrderItem setSalePrice(int salePrice) {
        this.salePrice = salePrice;
        return this;
    }

    @Override
    public OrderItem setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public OrderItem setOrderItemPriceDetails(Set<OrderItemPriceDetail> orderItemPriceDetails) {
        this.orderItemPriceDetails = orderItemPriceDetails;
        return this;
    }

    @Override
    public OrderItem setCategory(Category category) {
        this.category = category;
        return this;
    }

    @Override
    public OrderItem setCandidateItemOffers(Set<CandidateItemOffer> candidateItemOffers) {
        this.candidateItemOffers = candidateItemOffers;
        return this;
    }

    @Override
    public OrderItem setOrderItemQualifiers(Set<OrderItemQualifier> orderItemQualifiers) {
        this.orderItemQualifiers = orderItemQualifiers;
        return this;
    }

    @Override
    public OrderItem setDiscountingAllowed(boolean discountingAllowed) {
        this.discountingAllowed = discountingAllowed;
        return this;
    }

    @Override
    public OrderItem setDiscountValue(int discountValue) {
        this.discountValue = discountValue;
        return this;
    }

    @Override
    public OrderItem addCandidateItemOffer(CandidateItemOffer candidateItemOffer) {
        getCandidateItemOffers().add(candidateItemOffer);
        return this;
    }

    @Override
    public OrderItem removeAllCandidateItemOffers() {
        Set<CandidateItemOffer> candidates = getCandidateItemOffers();
        if(candidates != null){

            for(CandidateItemOffer candidateItemOffer: candidates){
                candidateItemOffer.setOrderItem(null);
            }
            candidates.clear();
        }
        return this;
    }

    @Override
    public int removeAllAdjustments() {
        int removedAdjustmentCount = 0;
        Set<OrderItemPriceDetail> details = getOrderItemPriceDetails();
        if(details != null){
            for(OrderItemPriceDetail priceDetail: details){
                priceDetail.setOrderItem(null);
            }
            removedAdjustmentCount = details.size();
            details.clear();
        }
        return removedAdjustmentCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItemImpl orderItem = (OrderItemImpl) o;

        if (getId() != orderItem.getId()) return false;
        if (getProductId() != orderItem.getProductId()) return false;
        if (getOriginPrice() != orderItem.getOriginPrice()) return false;
        if (getRetailPrice() != orderItem.getRetailPrice()) return false;
        if (getSalePrice() != orderItem.getSalePrice()) return false;
        if (getQuantity() != orderItem.getQuantity()) return false;
        if (isDiscountingAllowed() != orderItem.isDiscountingAllowed()) return false;
        if (getDiscountValue() != orderItem.getDiscountValue()) return false;
        if (getProductName() != null ? !getProductName().equals(orderItem.getProductName()) : orderItem.getProductName() != null)
            return false;
        if (getProductAttributeType() != null ? !getProductAttributeType().equals(orderItem.getProductAttributeType()) : orderItem.getProductAttributeType() != null)
            return false;
        return (color != null ? !color.equals(orderItem.color) : orderItem.color != null);
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (int) (getProductId() ^ (getProductId() >>> 32));
        result = 31 * result + (getProductName() != null ? getProductName().hashCode() : 0);
        result = 31 * result + (getProductAttributeType() != null ? getProductAttributeType().hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + getOriginPrice();
        result = 31 * result + getRetailPrice();
        result = 31 * result + getSalePrice();
        result = 31 * result + getQuantity();
        result = 31 * result + (isDiscountingAllowed() ? 1 : 0);
        result = 31 * result + getDiscountValue();
        return result;
    }

    @Override
    public String toString() {
        return "OrderItemImpl{" +
                "id=" + id +
                ", productId=" + productId +
                ", order=" + order.getOrderNumber() +
                ", productName=" + productName +
                ", productAttributeType=" + productAttributeType +
                ", color=" + color +
                ", originPrice=" + originPrice +
                ", retailPrice=" + retailPrice +
                ", salePrice=" + salePrice +
                ", quantity=" + quantity +
                ", orderItemPriceDetails=" + orderItemPriceDetails +
                ", category=" + category +
                ", candidateItemOffers=" + candidateItemOffers +
                ", orderItemQualifiers=" + orderItemQualifiers +
                ", discountingAllowed=" + discountingAllowed +
                ", discountValue=" + discountValue +
                '}';
    }
}