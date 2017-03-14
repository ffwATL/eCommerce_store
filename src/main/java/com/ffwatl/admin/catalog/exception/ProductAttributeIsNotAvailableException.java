package com.ffwatl.admin.catalog.exception;

/**
 * @author ffw_ATL.
 */
public class ProductAttributeIsNotAvailableException extends Exception {

    private long productId;
    private long attributeId;
    private int qtyRequested;
    private int qtyAvailable;

    public ProductAttributeIsNotAvailableException(String msg){
        super(msg);
    }

    public ProductAttributeIsNotAvailableException(long productId, long attributeId, int qtyRequested, int qtyAvailable){
        super();
        init(productId, attributeId, qtyRequested, qtyAvailable);
    }

    public ProductAttributeIsNotAvailableException(String msg, long productId, long attributeId, int qtyRequested, int qtyAvailable){
        super(msg);
        init(productId, attributeId, qtyRequested, qtyAvailable);
    }

    private void init(long productId, long attributeId, int qtyRequested, int qtyAvailable){
        this.productId = productId;
        this.attributeId = attributeId;
        this.qtyRequested = qtyRequested;
        this.qtyAvailable = qtyAvailable;
    }


    public long getProductId() {
        return productId;
    }

    public long getAttributeId() {
        return attributeId;
    }

    public int getQtyRequested() {
        return qtyRequested;
    }

    public int getQtyAvailable() {
        return qtyAvailable;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public void setAttributeId(long attributeId) {
        this.attributeId = attributeId;
    }

    public void setQtyRequested(int qtyRequested) {
        this.qtyRequested = qtyRequested;
    }

    public void setQtyAvailable(int qtyAvailable) {
        this.qtyAvailable = qtyAvailable;
    }
}
