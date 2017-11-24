package com.ffwatl.admin.catalog.service;

import com.ffwatl.admin.catalog.domain.ProductCategory;
import com.ffwatl.admin.catalog.domain.CommonCategory;
import com.ffwatl.admin.catalog.domain.Product;
import com.ffwatl.admin.catalog.domain.ProductAttribute;
import com.ffwatl.admin.catalog.domain.dto.response.AccessMode;
import com.ffwatl.common.persistence.FetchMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ffw_ATL.
 */
@Service("catalog_service")
@Transactional(readOnly = true)
public class CatalogServiceImpl implements CatalogService {

    @Resource(name = "product_service")
    private ProductService productService;

    @Resource(name = "product_attribute_service")
    private ProductAttributeService productAttributeService;


    @Override
    public Product createProduct() {
        return null;
    }

    @Override
    public Product saveProduct(Product product) {
        return productService.save(product);
    }

    @Override
    public Product findProductById(long id, FetchMode fetchMode) {
        return productService.findById(id, fetchMode, AccessMode.CUSTOMER);
    }

    @Override
    public List<Product> findProductsByName(String name, int start, int maxResult, FetchMode fetchMode) {
        return null;
    }

    @Override
    public List<Product> findProductsByStatus(boolean isActive, FetchMode fetchMode) {
        return null;
    }

    @Override
    public List<Product> findProductsByStatusAndCategory(boolean isActive, ProductCategory productCategory, FetchMode fetchMode) {
        return null;
    }

    @Override
    public List<Product> findAllProducts(FetchMode fetchMode) {
        return null;
    }

    @Override
    public void removeProduct(Product product) {

    }

    @Override
    public ProductCategory saveCategory(ProductCategory productCategory) {
        return null;
    }

    @Override
    public void saveCategoriesInBulk(List<? extends ProductCategory> list) {

    }

    @Override
    public void removeCategory(ProductCategory productCategory) {

    }

    @Override
    public ProductCategory findCategoryById(long categoryId, FetchMode fetchMode) {
        return null;
    }

    @Override
    public List<ProductCategory> findAllCategoriesUsed(FetchMode fetchMode) {
        return null;
    }

    @Override
    public List<ProductCategory> findGenderGroups(FetchMode fetchMode) {
        return null;
    }

    @Override
    public List<ProductCategory> findCategoriesByDepthLevel(int level, FetchMode fetchMode) {
        return null;
    }

    @Override
    public List<ProductCategory> findCategoriesByCat(CommonCategory cat, FetchMode fetchMode) {
        return null;
    }

    @Override
    public List<ProductCategory> findCategoriesByDepthLevelAndName(int level, String name, FetchMode fetchMode) {
        return null;
    }

    @Override
    public ProductAttribute findProductAttributeById(long id, FetchMode fetchMode) {
        ProductAttribute attribute = null;
        try {
             attribute = productAttributeService.findById(id, fetchMode);
        }catch (Exception e){
            e.printStackTrace();
        }
        return attribute;
    }
}