package com.ffwatl.admin.catalog.service;

import com.ffwatl.admin.catalog.dao.ProductAttributeTemplateDao;
import com.ffwatl.admin.catalog.domain.ProductAttributeTemplate;
import com.ffwatl.admin.catalog.domain.ProductAttributeTemplateImpl;
import com.ffwatl.admin.catalog.domain.dto.ProductAttributeTemplateDTO;
import com.ffwatl.admin.catalog.domain.dto.response.AccessMode;
import com.ffwatl.common.persistence.FetchMode;
import com.ffwatl.common.service.Converter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.ffwatl.common.service.ConvertToType.DTO_OBJECT;

/**
 * @author mmed 11/17/17
 */
@Service("product_attribute_template_service")
@Transactional(readOnly = true)
public class ProductAttributeTemplateServiceImpl extends Converter<ProductAttributeTemplate> implements ProductAttributeTemplateService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Resource(name = "product_attribute_template_dao")
    private ProductAttributeTemplateDao attributeTemplateDao;



    @Override
    public ProductAttributeTemplate findById(long id, FetchMode fetchMode) {
        LOGGER.trace("findById --> id={}, fetchMode={}", id, fetchMode);

        if (id < 1) {
            LOGGER.error("findById --> wrong id='{}' is given", id);
            throw new IllegalArgumentException("wrong 'ProductAttributeTemplate' id is given: " + id);
        }
        ProductAttributeTemplate attributeTemplate = attributeTemplateDao.findById(id, fetchMode);
        attributeTemplate = transformEntity2DTO(attributeTemplate, fetchMode, AccessMode.ALL);

        LOGGER.trace("findById --> {}", attributeTemplate);
        return attributeTemplate;
    }

    @Override
    public List<ProductAttributeTemplate> findAll(FetchMode fetchMode) {
        LOGGER.trace("findAll --> fetchMode={}", fetchMode);

        List<ProductAttributeTemplate> result = attributeTemplateDao.findAll(fetchMode);
        result = transformList(result, DTO_OBJECT, fetchMode, AccessMode.ALL);
        LOGGER.trace("findAll --> {}", result);
        return result;
    }

    @Override
    @Transactional
    public void save(ProductAttributeTemplate attributeTemplate) {
        LOGGER.trace("save --> {}", attributeTemplate);
        if (attributeTemplate == null) {
            LOGGER.error("save --> Can't persist entity because it's null");
            throw new IllegalArgumentException("Can't persist ProductAttributeTemplate entity because it is null");
        }
        attributeTemplateDao.save(attributeTemplate);
    }

    @Override
    @Transactional
    public void removeById(long id) {
        LOGGER.trace("removeById --> id={}", id);
        if (id < 1) {
            LOGGER.error("removeById --> wrong id='{}' is given", id);
            throw new IllegalArgumentException("Wrong 'ProductAttributeTemplate' id is given: id=" + id);
        }
        ProductAttributeTemplate attributeTemplate = attributeTemplateDao.findById(id, FetchMode.LAZY);
        remove(attributeTemplate);
    }

    @Override
    @Transactional
    public void remove(ProductAttributeTemplate attributeTemplate) {
        LOGGER.trace("remove --> {}", attributeTemplate);
        if (attributeTemplate == null) {
            LOGGER.error("remove --> Can't remove 'ProductAttributeTemplate' entity because it is null");
        }else if (!(attributeTemplate instanceof ProductAttributeTemplateImpl)) {
            attributeTemplate = attributeTemplateDao.findById(attributeTemplate.getId(), FetchMode.LAZY);
        }

        attributeTemplateDao.remove(attributeTemplate);
    }

    @Override
    public ProductAttributeTemplate transformDTO2Entity(ProductAttributeTemplate old, FetchMode fetchMode, AccessMode accessMode) {
        ProductAttributeTemplate entity = new ProductAttributeTemplateImpl();

        if (fetchMode == FetchMode.FETCHED) {
            entity
                    .setAttributeNames(old.getAttributeNames())
                    .setFields(old.getFields());
        }
        return entity
                .setId(old.getId())
                .setTemplateName(old.getTemplateName());
    }

    @Override
    public ProductAttributeTemplate transformEntity2DTO(ProductAttributeTemplate old, FetchMode fetchMode, AccessMode accessMode) {
        ProductAttributeTemplate dto = new ProductAttributeTemplateDTO();

        if (fetchMode == FetchMode.FETCHED) {
            dto
                    .setFields(old.getFields())
                    .setAttributeNames(old.getAttributeNames());
        }
        return dto
                .setId(old.getId())
                .setTemplateName(old.getTemplateName());
    }

}