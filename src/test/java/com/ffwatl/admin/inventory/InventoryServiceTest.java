package com.ffwatl.admin.inventory;

import com.ffwatl.admin.catalog.domain.ProductAttribute;
import com.ffwatl.admin.catalog.domain.ProductAttributeImpl;
import com.ffwatl.admin.catalog.service.CatalogService;
import com.ffwatl.common.persistence.FetchMode;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * @author ffw_ATL.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/inventory/inventory-test-config.xml"})
public class InventoryServiceTest {

    @Resource(name = "catalog_service")
    private CatalogService catalogService;

    @Resource(name = "inventory_service")
    private InventoryService inventoryService;

    @Rule
    public ExpectedException exception = ExpectedException.none();



    @Test
    public void testBeanCreation() {
        assertThat(catalogService, is(notNullValue()));
        assertThat(inventoryService, is(notNullValue()));
    }

    @Test
    public void testRetrieveQuantityAvailable_nullAttr() {
        assertThat(inventoryService.retrieveQuantityAvailable(null), is(equalTo(0)));
    }

    @Test
    public void testRetrieveQuantityAvailable_notNullAttr(){
        int quantityAvailable = 5;
        long attrId = 1;

        ProductAttribute attr = new ProductAttributeImpl()
                .setQuantity(quantityAvailable)
                .setId(attrId);

        when(catalogService.findProductAttributeById(attrId, FetchMode.LAZY)).thenReturn(attr);

        int result = inventoryService.retrieveQuantityAvailable(attr);

        assertThat(quantityAvailable, is(equalTo(result)));
    }

    @Test
    public void testRetrieveQuantityAvailable_notExistAttr() {
        long attrId = 9;
        int quantityAvailable = 0;

        ProductAttribute attr = new ProductAttributeImpl()
                .setId(attrId);

        int result = inventoryService.retrieveQuantityAvailable(attr);

        assertThat(quantityAvailable, is(equalTo(result)));
    }

    @Test
    public void testIsAvailable_normal() {
        int quantityAvailable = 5;
        long attrId = 1;

        ProductAttribute attr = new ProductAttributeImpl()
                .setQuantity(quantityAvailable)
                .setId(attrId);

        when(catalogService.findProductAttributeById(attrId, FetchMode.LAZY)).thenReturn(attr);

        assertThat(inventoryService.isAvailable(attr, 5), is(true));
        assertThat(inventoryService.isAvailable(attr, 6), is(false));
    }

    @Test
    public void testIsAvailable_nullAttr() {
        assertThat(inventoryService.isAvailable(null, 0), is(false));
        assertThat(inventoryService.isAvailable(null, 1), is(false));
        assertThat(inventoryService.isAvailable(null, -1), is(false));
    }

    @Test
    public void testCheckBasicAvailablility_normal() {
        int quantityAvailable = 5;
        long attrId = 1;

        ProductAttribute attr = new ProductAttributeImpl()
                .setQuantity(quantityAvailable)
                .setId(attrId);

        assertThat(inventoryService.checkBasicAvailablility(attr), is(true));
    }

    @Test
    public void testCheckBasicAvailability_nullAttr() {
        assertThat(inventoryService.checkBasicAvailablility(null), is(false));
    }

    @Test
    public void testCheckBasicAvailability_notAvailable() {
        int quantityAvailable = 0;
        long attrId = 1;

        ProductAttribute attr = new ProductAttributeImpl()
                .setQuantity(quantityAvailable)
                .setId(attrId);

        assertThat(inventoryService.checkBasicAvailablility(attr), is(false));
    }

    @Test
    public void testDecrementInventory_normal() throws InventoryUnavailableException {
        int quantityAvailable = 5;
        int requestedQuantity = 3;
        int remainQuantity = quantityAvailable - requestedQuantity;

        long attrId = 1;

        ProductAttribute attr = new ProductAttributeImpl()
                .setQuantity(quantityAvailable)
                .setId(attrId);

        when(catalogService.findProductAttributeById(attrId, FetchMode.LAZY)).thenReturn(attr);
        inventoryService.decrementInventory(attr, requestedQuantity);

        assertThat(attr.getQuantity(), is(equalTo(remainQuantity)));
    }

    @Test
    public void testDecrementInventory_nullAttr() throws InventoryUnavailableException {
        exception.expect(IllegalArgumentException.class);
        inventoryService.decrementInventory(null, 0);
    }

    @Test
    public void testDecrementInventory_zeroQty() throws InventoryUnavailableException {
        int quantityAvailable = 5;
        int requestedQuantity = 0;

        long attrId = 1;

        ProductAttribute attr = new ProductAttributeImpl()
                .setQuantity(quantityAvailable)
                .setId(attrId);

        exception.expect(IllegalArgumentException.class);
        inventoryService.decrementInventory(attr, requestedQuantity);
    }

    @Test
    public void testDecrementInventory_unavailableQty() throws InventoryUnavailableException {
        int quantityAvailable = 5;
        int requestedQuantity = 6;

        long attrId = 1;

        ProductAttribute attr = new ProductAttributeImpl()
                .setQuantity(quantityAvailable)
                .setId(attrId);

        when(catalogService.findProductAttributeById(attrId, FetchMode.LAZY)).thenReturn(attr);

        exception.expect(InventoryUnavailableException.class);
        inventoryService.decrementInventory(attr, requestedQuantity);
    }

    @Test
    public void testDecrementInventoryMap_normal() throws InventoryUnavailableException {
        Map<ProductAttribute, Integer> attrQuantities = new HashMap<>();

        int quantityAvailable = 5;
        int requestedQuantity = 4;
        int remainQuantity = quantityAvailable - requestedQuantity;

        long attrId = 1;

        ProductAttribute attr = new ProductAttributeImpl()
                .setQuantity(quantityAvailable)
                .setId(attrId);

        when(catalogService.findProductAttributeById(attrId, FetchMode.LAZY)).thenReturn(attr);

        attrQuantities.put(attr, requestedQuantity);
        inventoryService.decrementInventory(attrQuantities);

        assertThat(attr.getQuantity(), is(equalTo(remainQuantity)));
    }

    @Test
    public void testDecrementInventoryMap_nullMap() throws InventoryUnavailableException {
        exception.expect(IllegalArgumentException.class);
        inventoryService.decrementInventory(null);
    }

    @Test
    public void testIncrementInventory_normal() throws InventoryUnavailableException {
        int quantityAvailable = 5;
        int requestedQuantity = 2;
        int remainQuantity = quantityAvailable + requestedQuantity;

        long attrId = 1;

        ProductAttribute attr = new ProductAttributeImpl()
                .setQuantity(quantityAvailable)
                .setId(attrId);

        when(catalogService.findProductAttributeById(attrId, FetchMode.LAZY)).thenReturn(attr);
        inventoryService.incrementInventory(attr, requestedQuantity);

        assertThat(attr.getQuantity(), is(equalTo(remainQuantity)));
    }

    @Test
    public void testIncrementInventory_nullAttr() throws InventoryUnavailableException {
        exception.expect(IllegalArgumentException.class);
        inventoryService.incrementInventory(null, 1);
    }

    @Test
    public void testIncrementInventory_zeroQty() throws InventoryUnavailableException {
        int quantityAvailable = 5;
        int requestedQuantity = 0;

        long attrId = 1;

        ProductAttribute attr = new ProductAttributeImpl()
                .setQuantity(quantityAvailable)
                .setId(attrId);

        exception.expect(IllegalArgumentException.class);
        inventoryService.incrementInventory(attr, requestedQuantity);
    }

    @Test
    public void testIncrementInventoryMap_normal() throws InventoryUnavailableException {
        Map<ProductAttribute, Integer> attrQuantities = new HashMap<>();

        int quantityAvailable = 5;
        int requestedQuantity = 2;
        int remainQuantity = quantityAvailable + requestedQuantity;

        long attrId = 1;

        ProductAttribute attr = new ProductAttributeImpl()
                .setQuantity(quantityAvailable)
                .setId(attrId);

        attrQuantities.put(attr, requestedQuantity);

        when(catalogService.findProductAttributeById(attrId, FetchMode.LAZY)).thenReturn(attr);
        inventoryService.incrementInventory(attrQuantities);

        assertThat(attr.getQuantity(), is(equalTo(remainQuantity)));
    }

    @Test
    public void testIncrementInventoryMap_nullMap() throws InventoryUnavailableException {
        exception.expect(IllegalArgumentException.class);
        inventoryService.incrementInventory(null);
    }




}