package com.ffwatl.admin.inventory;

import com.ffwatl.admin.catalog.domain.ProductAttribute;
import com.ffwatl.admin.catalog.service.CatalogService;
import com.ffwatl.common.persistence.FetchMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author ffw_ATL.
 */
@Service("inventory_service")
@Transactional(readOnly = true)
public class InventoryServiceImpl implements InventoryService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Resource(name = "catalog_service")
    private CatalogService catalogService;


    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public int retrieveQuantityAvailable(ProductAttribute attribute) {
        if(attribute == null) {
            return 0;
        }
        attribute = catalogService.findProductAttributeById(attribute.getId(), FetchMode.LAZY);

        return attribute != null ? attribute.getQuantity() : 0;
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public boolean isAvailable(ProductAttribute attribute, int quantity) {
        if(attribute == null || quantity < 1){
            return false;
        }
        attribute = catalogService.findProductAttributeById(attribute.getId(),FetchMode.LAZY);
        return attribute != null && attribute.getQuantity() >= quantity;
    }

    @Override
    public boolean checkBasicAvailablility(ProductAttribute attribute) {
        return attribute != null && attribute.getQuantity() > 1;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void decrementInventory(ProductAttribute attribute, int requestedQuantity) throws InventoryUnavailableException {
        if(attribute == null || requestedQuantity < 1) {
            throw new IllegalArgumentException("Wrong parameters is given. Attribute: "+
                    attribute + ", requestedQuantity = " + requestedQuantity);
        }
        ProductAttribute freshAttribute = catalogService.findProductAttributeById(attribute.getId(), FetchMode.LAZY);

        if(freshAttribute == null) {
            throw new IllegalArgumentException("There is no such ProductAttribute. Requested id: "+ attribute.getId());
        }

        int availableQuantity = freshAttribute.getQuantity();

        if(availableQuantity < requestedQuantity) {
            LOGGER.error("Could not update quantity for this product attribute: id = {}, requested = {}, " +
                            "available = {}", freshAttribute.getId(), requestedQuantity, availableQuantity);
            throw new InventoryUnavailableException(freshAttribute.getId(), requestedQuantity, availableQuantity);
        }
        int remainQuantity = availableQuantity - requestedQuantity;

        freshAttribute.setQuantity(remainQuantity);
        attribute.setQuantity(remainQuantity);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void decrementInventory(Map<ProductAttribute, Integer> attrQuantities) throws InventoryUnavailableException {
        if(attrQuantities == null) {
            throw new IllegalArgumentException("Given Map<ProductAttribute, Integer> is: null");
        }
        for(ProductAttribute attr: attrQuantities.keySet()){
            decrementInventory(attr, attrQuantities.get(attr));
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void incrementInventory(ProductAttribute attribute, int requestedQuantity) throws InventoryUnavailableException {
        if(attribute == null || requestedQuantity < 1) {
            throw new IllegalArgumentException("Wrong parameters is given. Attribute: "+
                    attribute + ", requestedQuantity = " + requestedQuantity);
        }
        ProductAttribute freshAttribute = catalogService.findProductAttributeById(attribute.getId(), FetchMode.LAZY);

        if(freshAttribute == null) {
            throw new IllegalArgumentException("There is no such ProductAttribute. Requested id: "+ attribute.getId());
        }

        int availableQuantity = freshAttribute.getQuantity();
        int remainQuantity = availableQuantity + requestedQuantity;

        freshAttribute.setQuantity(remainQuantity);
        attribute.setQuantity(remainQuantity);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void incrementInventory(Map<ProductAttribute, Integer> attrQuantities) throws InventoryUnavailableException {
        if(attrQuantities == null) {
            throw new IllegalArgumentException("Given Map<ProductAttribute, Integer> is: null");
        }
        for(ProductAttribute attr: attrQuantities.keySet()) {
            incrementInventory(attr, attrQuantities.get(attr));
        }
    }
}