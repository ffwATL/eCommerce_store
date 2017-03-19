package com.ffwatl.admin.order.service;

import com.ffwatl.admin.order.dao.OrderNumberDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ffw_ATL.
 */
@Service("order_number_service")
public class OrderNumberServiceImpl implements OrderNumberService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Resource
    private OrderNumberDao orderNumberDao;

    @Override
    public int findLastOrderNumber() {
        try {
            return Integer.valueOf(orderNumberDao.findLastOrderNumber());
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
}