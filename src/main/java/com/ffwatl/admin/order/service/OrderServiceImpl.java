package com.ffwatl.admin.order.service;

import com.ffwatl.admin.order.domain.Order;
import org.springframework.stereotype.Service;



@Service("order_service")
public class OrderServiceImpl implements OrderService {

    @Override
    public Order save(Order order, Boolean priceOrder) {
        return null;
    }
}