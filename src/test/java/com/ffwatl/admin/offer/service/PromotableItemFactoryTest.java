package com.ffwatl.admin.offer.service;


import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.offer.service.discount.PromotableItemFactory;
import com.ffwatl.admin.offer.service.discount.PromotableOrder;
import com.ffwatl.admin.offer.service.discount.PromotableOrderItem;
import com.ffwatl.admin.order.domain.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ContextConfiguration({"/spring/spring-application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)

public class PromotableItemFactoryTest {

    @Resource(name = "promotable_item_factory")
    private PromotableItemFactory promotableItemFactory;

    @Resource(name = "offerForSaveTest")
    private Offer offerForSaveTest;

    @Resource(name = "namedOrderWithId")
    private Order namedOrderWithId;

    @Test
    public void testCreatePromotableOrder() throws Exception {
        assertNotNull(namedOrderWithId);

        PromotableOrder promotableOrder = promotableItemFactory.createPromotableOrder(namedOrderWithId, true);

        assertNotNull(promotableOrder);

        List<PromotableOrderItem> promotableOrderItemList = promotableOrder.getAllOrderItems();
        assertNotNull(promotableOrderItemList);
        assertEquals(1, promotableOrderItemList.size());

        System.err.println("promotableOrder.calculateItemAdjustmentTotal(): " + promotableOrder.calculateItemAdjustmentTotal());
        System.err.println("promotableOrder.calculateOrderAdjustmentTotal(): "+ promotableOrder.calculateOrderAdjustmentTotal());
        System.err.println("promotableOrder.calculateSubtotalWithAdjustments(): "+ promotableOrder.calculateSubtotalWithAdjustments());

    }
}