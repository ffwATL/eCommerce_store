package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.dao.ProductCategoryDao;
import com.ffwatl.admin.catalog.domain.ProductAttributeTemplate;
import com.ffwatl.admin.catalog.domain.ProductCategory;
import com.ffwatl.admin.catalog.domain.ProductCategoryImpl;
import com.ffwatl.admin.catalog.domain.dto.ProductCategoryDTO;
import com.ffwatl.admin.catalog.domain.dto.response.AccessMode;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.common.persistence.FetchMode;
import com.ffwatl.common.service.Converter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static com.ffwatl.common.service.ConvertToType.DTO_OBJECT;

/**
 * Implementation of ProductCategoryService.
 */
@Service("product_category_service")
@Transactional(readOnly = true)
public class ProductCategoryServiceImpl extends Converter<ProductCategory> implements ProductCategoryService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Resource(name = "product_category_dao")
    private ProductCategoryDao productCategoryDao;

    @Resource(name = "user_service")
    private Converter<User> userConverter;

    @Resource(name = "product_attribute_template_service")
    private Converter<ProductAttributeTemplate> templateConverter;

    /**
     * Returns ProductCategoryImpl object from DB by given id value;
     * @param id ProductCategoryImpl identifier value;
     * @return ProductCategoryImpl object.
     */
    @Override
    public ProductCategory findById(long id, FetchMode fetchMode) {
        LOGGER.trace("findById --> id={}, fetchMode={}", id, fetchMode);
        if (id < 1) {
            LOGGER.error("findById --> wrong id={} is given", id);
            throw new IllegalArgumentException("Wrong 'ProductCategory' id is given: "+id);
        }

        ProductCategory productCategory = productCategoryDao.findById(id, fetchMode);
        productCategory = transformEntity2DTO(productCategory, fetchMode, AccessMode.ALL);
        LOGGER.trace("findById --> id={}, {}", id, productCategory);
        return productCategory;
    }

    /**
     * Persisting given ProductCategoryImpl object into DB.
     * if given parameter is null;
     * @param productCategory ProductCategory object to be persist into DB;
     */
    @Override
    @Transactional
    public void save(ProductCategory productCategory) {
        LOGGER.debug("save --> {}", productCategory);
        if(productCategory == null) {
            LOGGER.error("save --> can't save entity. Given ProductCategory object is null. Please check the business logic above");
            throw new IllegalArgumentException();
        }

        if(!(productCategory instanceof ProductCategoryImpl)) {
            LOGGER.trace("save --> need to transform DTO object to entity");
            FetchMode fetchMode = productCategory.getId() > 0 ? FetchMode.FETCHED : FetchMode.LAZY;
            productCategory = transformDTO2Entity(productCategory, fetchMode, AccessMode.ALL);
        }

        productCategoryDao.save(productCategory);
    }

    /**
     * Persisting given list of ProductCategoryImpl objects into DB. Throws IllegalArgumentException
     * if given parameter is null or if list size < 1;
     * @param list list of ProductCategoryImpl objects to persist in DB.
     */
    @Override
    @Transactional
    public void save(List<? extends ProductCategory> list) {
        if(list == null || list.size() < 1) {
            LOGGER.error("save --> can't save given list: {}", list);
            throw new IllegalArgumentException("Bad parameter: list = " + list);
        }
        list.forEach(this::save);
    }

    /**
     * Returns ProductCategoryDTO object by its name.
     * @param name name of the ProductCategory object to search;
     * @return ProductCategoryDTO object if it present in the DB or null if it's not.
     */
    @Override
    public ProductCategory findByName(@NotNull String name, FetchMode fetchMode) {
        LOGGER.trace("findByName --> name={}", name);
        List<ProductCategory> list = productCategoryDao.findByName(name, fetchMode);

        LOGGER.trace("findByName --> name={}, result={}", name, list);
        if(list.size() < 1) {
            return null;
        }

        ProductCategory result = list.get(0);
        return transformEntity2DTO(result, fetchMode, AccessMode.ALL);
    }

    /**
     * Returns ProductCategoryDTO object from the DB by its depth level and name;
     * @param level depth level value;
     * @param name name of the ProductCategory object to search;
     * @return ProductCategoryDTO object if it present in the DB or null if it not.
     */
    @Override
    public ProductCategory findByLvlAndByName(int level, @NotNull String name, FetchMode fetchMode) {
        LOGGER.trace("findByLvlAndByName --> level={}, name={}, fetchMode={}", level, name, fetchMode);
        List<ProductCategory> list = productCategoryDao.findByLevelAndName(level, name, fetchMode);

        if (list == null || list.isEmpty()) {
            LOGGER.warn("findByLvlAndByName --> nothing is found by given parameters: level={}, name={}, fetchMode={}", level, name, fetchMode);
            return null;
        }
        ProductCategory result = list.get(0);
        LOGGER.trace("findByLvlAndByName --> level={}, name={}, fetchMode={}, {}", level, name, fetchMode, result);
        return transformEntity2DTO(result, fetchMode, AccessMode.ALL);
    }

    /**
     * Returns list of ProductCategoryImpl object from the DB by its depth level.
     * @param level depth level value;
     * @return list of ProductCategoryImpl objects without children.
     */
    @Override
    public List<ProductCategory> findByLevel(int level, FetchMode fetchMode) {
        LOGGER.trace("findByLevel --> level={}, fetchMode={}", level, fetchMode);

        if(level < 0) {
            LOGGER.error("findByLevel --> wrong level={} is given", level);
            throw new IllegalArgumentException("wrong ProductCategory.level is given: " + level);
        }
        List<ProductCategory> list = productCategoryDao.findByLevel(level, fetchMode);
        LOGGER.trace("findByLevel --> level={}, fetchMode={}\n{}", list);

        return transformList(list, DTO_OBJECT, fetchMode, AccessMode.ALL);
    }

    @Override
    public List<ProductCategory> findAllUsed(FetchMode fetchMode) {
        List<ProductCategory> result = productCategoryDao.findAllInUse(fetchMode);
        LOGGER.trace("findAllUsed --> fetchMode={}\n{}", fetchMode, result);
        return transformList(result, DTO_OBJECT, fetchMode, AccessMode.ALL);
    }

    @Override
    public ProductCategory transformDTO2Entity(ProductCategory old, FetchMode fetchMode, AccessMode accessMode) {
        User userEntity = userConverter.transformDTO2Entity(old.getCreatedBy(), fetchMode, accessMode);
        ProductAttributeTemplate template = templateConverter.transformDTO2Entity(old.getProductAttributeTemplate(), fetchMode, accessMode);

        ProductCategory parent = old.getParent();

        if (parent != null && !(parent instanceof ProductCategoryImpl)) {
            parent = new ProductCategoryImpl()
                    .setId(parent.getId())
                    .setCategoryName(parent.getCategoryName())
                    .setDescription(parent.getDescription())
                    .setLevel(parent.getLevel())
                    .setWeight(parent.getWeight());
        }

        // assumed that if FetchMode.LAZY then there is an empty child list
        ProductCategory result = new ProductCategoryDTO();
        List<ProductCategory> childListEntity = new ArrayList<>();

        if (fetchMode == FetchMode.FETCHED) {
            LOGGER.trace("transformDTO2Entity --> FETCHED mode detected, processing child..");
            for (ProductCategory c: old.getChild()) {
                c.setParent(result);
                /*c.setChild(new ArrayList<>());*/ // since we don't want them to be fetched
                childListEntity.add(transformDTO2Entity(c, fetchMode, accessMode));
            }
        }

        return result
                .setId(old.getId())
                .setChild(childListEntity)
                .setCreatedBy(userEntity)
                .setParent(parent).setCategoryName(old.getCategoryName())
                .setLevel(old.getLevel())
                .setWeight(old.getWeight())
                .setProductAttributeTemplate(template)
                .setDescription(old.getDescription());
    }

    @Override
    public ProductCategory transformEntity2DTO(ProductCategory old, FetchMode fetchMode, AccessMode accessMode) {
        User userDto = userConverter.transformEntity2DTO(old.getCreatedBy(), fetchMode, accessMode);
        ProductAttributeTemplate template = templateConverter.transformEntity2DTO(old.getProductAttributeTemplate(), fetchMode, accessMode);

        ProductCategory parent = old.getParent();

        if (parent != null && !(parent instanceof ProductCategoryDTO)) {
            parent = new ProductCategoryDTO()
                    .setId(parent.getId())
                    .setCategoryName(parent.getCategoryName())
                    .setDescription(parent.getDescription())
                    .setLevel(parent.getLevel())
                    .setWeight(parent.getWeight());
        }
        // assumed that if FetchMode.LAZY then there is an empty child list
        ProductCategory result = new ProductCategoryDTO();
        List<ProductCategory> childListDto = new ArrayList<>();

        if (fetchMode == FetchMode.FETCHED) {
            LOGGER.trace("transformEntity2DTO --> FETCHED mode detected, processing child..");
            for (ProductCategory c: old.getChild()) {
                c.setParent(result);
                c.setChild(new ArrayList<>()); // since we don't want them to be fetched
                childListDto.add(transformEntity2DTO(c, fetchMode, accessMode));
            }
        }

        return result
                .setId(old.getId())
                .setChild(childListDto)
                .setCreatedBy(userDto)
                .setParent(parent).setCategoryName(old.getCategoryName())
                .setLevel(old.getLevel())
                .setWeight(old.getWeight())
                .setProductAttributeTemplate(template)
                .setDescription(old.getDescription());
    }
}