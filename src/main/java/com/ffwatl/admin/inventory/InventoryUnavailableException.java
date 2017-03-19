package com.ffwatl.admin.inventory;

/**
 * @author ffw_ATL.
 */
public class InventoryUnavailableException extends Exception {

    private static final long serialVersionUID = 1L;

    protected long attributeId;

    protected int quantityRequested;

    protected int quantityAvailable;

    public InventoryUnavailableException(String msg) {
        super(msg);
    }

    public InventoryUnavailableException(long attributeId, int quantityRequested, int quantityAvailable) {
        super();
        this.attributeId = attributeId;
        this.quantityAvailable = quantityAvailable;
        this.quantityRequested = quantityRequested;
    }

    public InventoryUnavailableException(String arg0, long attributeId, int quantityRequested, int quantityAvailable) {
        super(arg0);
        this.attributeId = attributeId;
        this.quantityAvailable = quantityAvailable;
        this.quantityRequested = quantityRequested;
    }

    public InventoryUnavailableException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public long getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(long attributeId) {
        this.attributeId = attributeId;
    }

    public int getQuantityRequested() {
        return quantityRequested;
    }

    public void setQuantityRequested(int quantityRequested) {
        this.quantityRequested = quantityRequested;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }
}