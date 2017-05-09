package com.ffwatl.common.spring;

import com.ffwatl.admin.catalog.service.CatalogService;
import com.ffwatl.admin.catalog.service.CatalogServiceImpl;
import org.springframework.beans.factory.FactoryBean;

import static org.powermock.api.mockito.PowerMockito.mock;

/**
 * @author ffw_ATL.
 */
public class CatalogServiceFactoryBean implements FactoryBean<CatalogService> {

    private CatalogServiceImpl catalogService = mock(CatalogServiceImpl.class);

    @Override
    public CatalogService getObject() throws Exception {
        return catalogService;
    }

    @Override
    public Class<?> getObjectType() {
        return catalogService.getClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}