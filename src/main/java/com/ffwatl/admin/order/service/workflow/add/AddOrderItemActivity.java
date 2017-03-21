package com.ffwatl.admin.order.service.workflow.add;

import com.ffwatl.admin.catalog.domain.Category;
import com.ffwatl.admin.catalog.domain.Color;
import com.ffwatl.admin.catalog.domain.Product;
import com.ffwatl.admin.catalog.service.CatalogService;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.domain.OrderItemColor;
import com.ffwatl.admin.order.service.OrderItemService;
import com.ffwatl.admin.order.service.OrderService;
import com.ffwatl.admin.order.service.call.OrderItemRequest;
import com.ffwatl.admin.order.service.call.OrderItemRequestDTO;
import com.ffwatl.admin.order.service.workflow.CartOperationRequest;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;
import com.ffwatl.common.persistence.FetchMode;

import javax.annotation.Resource;

/**
 * @author ffw_ATL.
 */
public class AddOrderItemActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {

    @Resource(name = "order_service")
    private OrderService orderService;

    @Resource(name = "order_item_service")
    private OrderItemService orderItemService;

    @Resource(name = "catalog_service")
    private CatalogService catalogService;

    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();
        OrderItemRequestDTO orderItemRequestDTO = request.getItemRequest();

        // Order has been verified in a previous activity -- the values in the request can be trusted
        Order order = request.getOrder();


        Product product = null;
        if (orderItemRequestDTO.getProductId() > 0) {
            product = catalogService.findProductById(orderItemRequestDTO.getProductId(), FetchMode.FETCHED);
        }
        if(product == null) {
            throw new IllegalArgumentException("Can't find a product requested with id: " + orderItemRequestDTO.getProductId());
        }
        Category category = product.getCategory();
        Color color = product.getColor();

        OrderItem item;

        OrderItemRequest itemRequest = new OrderItemRequest();
        itemRequest.setQuantity(orderItemRequestDTO.getQuantity());
        itemRequest.setRetailPriceOverride(orderItemRequestDTO.getOverrideRetailPrice());
        itemRequest.setSalePriceOverride(orderItemRequestDTO.getOverrideSalePrice());
        itemRequest.setItemName(orderItemRequestDTO.getItemName());
        itemRequest.setOrder(order);
        itemRequest.setCategory(category);
        itemRequest.setProductAttribute(orderItemRequestDTO.getProductAttribute());
        itemRequest.setColor(new OrderItemColor()
                .setColor(color.getColor())
                .setHex(color.getHex()));
        itemRequest.setProduct(product);

        item = orderItemService.createOrderItem(itemRequest);

        order.getOrderItems().add(item);
        request.setOrderItem(item);

        if (!request.isPriceOrder()) {
            //persist the newly created order if we're not going through the pricing flow. This helps with proper
            //fulfillment group association
            orderItemService.saveOrderItem(item);
        }

        return context;
    }
}