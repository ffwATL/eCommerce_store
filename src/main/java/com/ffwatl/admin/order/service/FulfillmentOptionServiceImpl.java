package com.ffwatl.admin.order.service;

import com.ffwatl.admin.order.dao.FulfillmentOptionDao;
import com.ffwatl.admin.order.domain.FulfillmentOption;
import com.ffwatl.admin.order.service.type.FulfillmentType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ffw_ATL.
 */
@Service("fulfillment_option_service")
@Transactional(readOnly = true)
public class FulfillmentOptionServiceImpl implements FulfillmentOptionService {

    @Resource(name = "fulfillment_option_dao")
    private FulfillmentOptionDao fulfillmentOptionDao;

    @Override
    public FulfillmentOption findFulfillmentOptionById(long id) {
        return fulfillmentOptionDao.findFulfillmentOptionById(id);
    }

    @Override
    @Transactional
    public FulfillmentOption save(FulfillmentOption option) {
        if(option == null) throw new IllegalArgumentException("Given FulfillmentOption is null");
        return fulfillmentOptionDao.save(option);
    }

    @Override
    public List<FulfillmentOption> findAllFulfillmentOptions() {
        return fulfillmentOptionDao.findAllFulfillmentOptions();
    }

    @Override
    public List<FulfillmentOption> findAllFulfillmentOptionsByFulfillmentType(FulfillmentType type) {
        return fulfillmentOptionDao.findAllFulfillmentOptionsByFulfillmentType(type);
    }

}
