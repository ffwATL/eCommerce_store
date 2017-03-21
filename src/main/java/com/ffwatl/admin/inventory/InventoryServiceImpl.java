package com.ffwatl.admin.inventory;

import com.ffwatl.admin.catalog.domain.ProductAttribute;
import com.ffwatl.admin.catalog.service.CatalogService;
import com.ffwatl.common.persistence.FetchMode;
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

    @Resource(name = "catalog_service")
    private CatalogService catalogService;

    @Resource(name = "inventory_service_extension_manager")
    private InventoryServiceExtensionManager extensionManager;


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
        if(attribute == null){
            return false;
        }
        attribute = catalogService.findProductAttributeById(attribute.getId(),FetchMode.LAZY);
        return attribute != null && attribute.getQuantity() >= quantity;
    }

    @Override
    public boolean checkBasicAvailablility(ProductAttribute attribute) {
        return !(attribute == null || attribute.getQuantity() < 1);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void decrementInventory(ProductAttribute attribute, int requestedQuantity) throws InventoryUnavailableException {
        if(attribute == null) {
            throw new IllegalArgumentException("Given ProductAttribute is null");
        }
        attribute = catalogService.findProductAttributeById(attribute.getId(), FetchMode.LAZY);

        int availableQuantity = attribute.getQuantity();

        if(availableQuantity < requestedQuantity) {
            throw new InventoryUnavailableException(attribute.getId(), requestedQuantity, availableQuantity);
        }
        attribute.setQuantity(availableQuantity - requestedQuantity);
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
        if(attribute == null) {
            throw new IllegalArgumentException("Given ProductAttribute is null");
        }
        attribute = catalogService.findProductAttributeById(attribute.getId(), FetchMode.LAZY);

        int availableQuantity = attribute.getQuantity();

        if(availableQuantity < requestedQuantity) {
            throw new InventoryUnavailableException(attribute.getId(), requestedQuantity, availableQuantity);
        }
        attribute.setQuantity(availableQuantity + requestedQuantity);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void incrementInventory(Map<ProductAttribute, Integer> attrQuantities) throws InventoryUnavailableException {
        if(attrQuantities == null) {
            throw new IllegalArgumentException("Given Map<ProductAttribute, Integer> is: null");
        }
        for(ProductAttribute attr: attrQuantities.keySet()){
            incrementInventory(attr, attrQuantities.get(attr));
        }
    }
}