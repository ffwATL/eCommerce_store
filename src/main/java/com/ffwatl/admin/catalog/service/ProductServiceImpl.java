package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.dao.ProductDao;
import com.ffwatl.admin.catalog.domain.*;
import com.ffwatl.admin.catalog.domain.dto.ProductDTO;
import com.ffwatl.admin.catalog.domain.dto.response.*;
import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.admin.user.service.UserService;
import com.ffwatl.common.persistence.FetchMode;
import com.ffwatl.common.service.Converter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

import static com.ffwatl.common.service.ConvertToType.DTO_OBJECT;

@Service("product_service")
public class ProductServiceImpl extends Converter<Product> implements ProductService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Resource(name = "product_dao")
    private ProductDao productDao;

    @Resource(name = "brand_service")
    private BrandService brandService;

    @Resource(name = "product_category_service")
    private ProductCategoryService productCategoryService;

    @Resource(name = "color_service")
    private ColorService colorService;

    @Resource(name = "user_service")
    private UserService userService;



    @Override
    public Product findById(long id, FetchMode fetchMode, AccessMode accessMode) {

        if (id < 1) {
            LOGGER.error("findById --> wrong 'Product' id={} is given", id);
            throw new IllegalArgumentException("Wrong 'Product' id given: " + id);
        }
        LOGGER.trace("findById --> id={}, fetchMode={}", id, fetchMode);

        Product product = productDao.findById(id, fetchMode);
        product = transformEntity2DTO(product, fetchMode, accessMode);

        LOGGER.trace("findById --> {}", product);
        return product;
    }

    @Override
    public List<Product> findAll(FetchMode fetchMode, AccessMode accessMode) {
        LOGGER.trace("findAll --> fetchMode={}, accessMode={}", fetchMode, accessMode);
        List<Product> productList = productDao.findAll(fetchMode);
        productList = transformList(productList, DTO_OBJECT, fetchMode, accessMode);

        LOGGER.trace("findAll --> {}", productList);
        return productList;
    }

    @Override
    @Transactional
    public Product save(Product product) {
        if (product == null) {
            LOGGER.error("save --> Can't save 'Product' entity because it's null");
            throw new IllegalArgumentException("Can't save 'Product' entity because it's null");
        }
        LOGGER.trace("save --> {}", product);

        if (!(product instanceof ProductImpl)) {
            product = transformDTO2Entity(product, FetchMode.FETCHED, AccessMode.ADMIN_ONLY);
        }
        return productDao.save(product);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void remove(Product product) {

        if (product == null) {
            LOGGER.error("remove --> Can't remove 'Product' entity because it's null");
            throw new IllegalArgumentException("Can't remove 'Product' entity because it's null");
        }
        LOGGER.trace("remove --> {}", product);
        removeById(product.getId());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void removeById(long id) {
        if (id < 1) {
            LOGGER.error("removeById --> Wrong product id={} is given", id);
            throw new IllegalArgumentException("Wrong product id is given: " + id);
        }
        LOGGER.debug("removeById --> Removing 'Product' with id={}", id);

        Product product = productDao.findById(id, FetchMode.LAZY);
        productDao.remove(product);
        LOGGER.trace("removeById --> Removed 'Product' with id={}", id);
    }

    @Override
    @Transactional
    public void changeItemStatus(Product request) {
        if (request == null || request.getId() < 1 || request.isActive() == null) {
            LOGGER.error("changeItemStatus --> given wrong 'ProductRequest' object. Please check logic above. {}", request);
            throw new IllegalArgumentException("given wrong 'ProductRequest' object. Please check logic above. " + request);
        }
        final long id = request.getId();
        final boolean isActive = request.isActive();
        LOGGER.trace("changeItemStatus --> changing Product id={}, isActive={}", id, isActive);

        Product product = findById(id, FetchMode.LAZY, AccessMode.ADMIN_ONLY);
        product.setActive(isActive);
    }

    @Override
    @Transactional
    public void processSingleProductRequest(Product request) {
        if (request == null || request.getId() < 1) {
            LOGGER.error("processSingleProductRequest --> given wrong 'ProductRequest' object. Please check logic above. {}", request);
            throw new IllegalArgumentException("given wrong 'ProductRequest' object. Please check logic above. " + request);
        }

        final long id = request.getId();
        final Boolean isActive = request.isActive();
        final Boolean isUsed = request.isUsed();
        final I18n productName = request.getProductName();
        final I18n description = request.getDescription();

        final String vendorCode = request.getVendorCode();
        final String extraNotes = request.getExtraNotes();
        final String metaInfo = request.getMetaInfo();
        final String metaKeys = request.getMetaKeys();

        final Integer originPrice= request.getOriginPrice();
        final Integer retailPrice = request.getRetailPrice();
        final Integer salePrice = request.getSalePrice();
        final Currency currency = request.getCurrency();
        final Gender gender = request.getGender();

        Brand brand =request.getBrand();
        Color color = request.getColor();
        ProductCategory productCategory = request.getProductCategory();

        Product product = findById(id, FetchMode.LAZY, AccessMode.ADMIN_ONLY);

        if(product == null) {
            LOGGER.error("processSingleProductRequest --> Probably wrong Product id. Product not found for id={}", id);
            throw new IllegalArgumentException("Probably wrong Product id. Product not found for id="+id);
        }

        LOGGER.trace("processSingleProductRequest --> processing request: {}", request);

        if (isActive != null) {
            product.setActive(isActive);
        }
        if (isUsed != null) {
            product.setIsUsed(isUsed);
        }
        if (productName != null) {
            product.setProductName(productName); // all locales must be set anyway(!!) even if only one of them were updated
        }
        if (description != null) {
            product.setDescription(description);
        }
        if (vendorCode != null) {
            product.setVendorCode(vendorCode);
        }
        if (extraNotes != null) {
            product.setExtraNotes(extraNotes);
        }
        if (metaInfo != null) {
            product.setMetaInfo(metaInfo);
        }
        if (metaKeys != null) {
            product.setMetaKeys(metaKeys);
        }
        if (originPrice != null) {
            product.setOriginPrice(originPrice);
        }
        if (retailPrice != null) {
            product.setRetailPrice(retailPrice);
        }
        if (salePrice != null) {
            product.setSalePrice(salePrice);
        }
        if (currency != null) {
            product.setCurrency(currency);
        }
        if (gender != null) {
            product.setGender(gender);
        }

        if (brand != null) {
            brand = brandService.findById(brand.getId());
            product.setBrand(brand);
        }
        if (color != null) {
            color = colorService.findById(color.getId());
            product.setColor(color);
        }
        if (productCategory != null) {
            productCategory = productCategoryService.findById(productCategory.getId(), FetchMode.LAZY);
            product.setProductCategory(productCategory);
        }

        product.setLastChangeDate(new Timestamp(System.currentTimeMillis()));
    }

    @Override
    @Transactional
    public void processProductRequestInBulk(List<Product> productRequestList) {
        if (productRequestList == null) {
            LOGGER.error("processProductRequestInBulk --> wrong argument is given: null");
            throw new IllegalArgumentException("wrong argument is given: null");
        }

        LOGGER.trace("processProductRequestInBulk --> size: {}", productRequestList.size());

        for (Product r: productRequestList) {
            processSingleProductRequest(r);
        }
    }



    @Override
    public Product transformDTO2Entity(Product old, FetchMode fetchMode, AccessMode accessMode) {
        if (accessMode == null) {
            LOGGER.fatal("transformDTO2Entity --> can't start conversion because of given AccessMode = [null]");
            throw new IllegalArgumentException("Access mode can't be null");
        }else if (old instanceof ProductImpl) {
            return old;
        }

        Product entity = productDao.findById(old.getId(), fetchMode)
                .setId(old.getId())
                .setBrand(old.getBrand())
                .setActive(old.isActive())
                .setColor(old.getColor())
                .setCurrency(old.getCurrency())
                .setDescription(old.getDescription())
                .setExtraNotes(old.getExtraNotes())
                .setIsUsed(old.isUsed())
                .setMetaInfo(old.getMetaInfo())
                .setMetaKeys(old.getMetaKeys())
                .setNumberOfImages(old.getNumberOfImages())
                .setVendorCode(old.getVendorCode())
                .setProductCategory(old.getProductCategory())
                .setProductName(old.getProductName())
                .setSalePrice(old.getSalePrice())
                .setOriginPrice(old.getOriginPrice())
                .setRetailPrice(old.getRetailPrice());

        if (fetchMode == FetchMode.FETCHED) {
            entity.setProductAttributes(old.getProductAttributes());
        }

        processAccessMode(old, entity, accessMode);

        return entity;
    }

    @Override
    public Product transformEntity2DTO(Product old, FetchMode fetchMode, AccessMode accessMode) {
        if (accessMode == null) {
            LOGGER.fatal("transformDTO2Entity --> can't start conversion because of given AccessMode = [null]");
            throw new IllegalArgumentException("Access mode can't be null");
        }else if (old instanceof ProductDTO) {
            return old;
        }

        Product dto = new ProductDTO()
                .setId(old.getId())
                .setBrand(old.getBrand())
                .setActive(old.isActive())
                .setColor(old.getColor())
                .setCurrency(old.getCurrency())
                .setDescription(old.getDescription())
                .setExtraNotes(old.getExtraNotes())
                .setIsUsed(old.isUsed())
                .setMetaInfo(old.getMetaInfo())
                .setMetaKeys(old.getMetaKeys())
                .setNumberOfImages(old.getNumberOfImages())
                .setVendorCode(old.getVendorCode())
                .setProductCategory(old.getProductCategory())
                .setProductName(old.getProductName())
                .setSalePrice(old.getSalePrice())
                .setOriginPrice(old.getOriginPrice())
                .setRetailPrice(old.getRetailPrice());

        if (fetchMode == FetchMode.FETCHED) {
            dto.setProductAttributes(old.getProductAttributes());
        }

        processAccessMode(old, dto, accessMode);
        return null;
    }

    private void processAccessMode(Product old, Product fresh, AccessMode accessMode) {
        if (accessMode.getRole() < AccessMode.CUSTOMER.getRole()) {
            fresh
                    .setImportDate(old.getImportDate())
                    .setLastChangeDate(old.getLastChangeDate());
            User user;
            if (old.getAddedBy() != null && old instanceof ProductDTO) {
                user = userService.findById(old.getAddedBy().getId());

            }else {
                user = old.getAddedBy();
            }

            fresh.setAddedBy(user);
        }
    }
}