package com.ffwatl.admin.catalog.service;

import com.ffwatl.admin.catalog.domain.ProductAttributeTemplate;
import com.ffwatl.common.persistence.FetchMode;
import com.ffwatl.common.service.ConverterDTO;
import org.springframework.stereotype.Service;

/**
 * @author mmed 11/17/17
 */
@Service("product_attribute_template_service")
public class ProductAttributeTemplateServiceImpl extends ConverterDTO<ProductAttributeTemplate> implements ProductAttributeTemplateService {




    @Override
    public ProductAttributeTemplate transformDTO2Entity(ProductAttributeTemplate old, FetchMode fetchMode) {
        return null;
    }

    @Override
    public ProductAttributeTemplate transformEntity2DTO(ProductAttributeTemplate old, FetchMode fetchMode) {
        return null;
    }
}
