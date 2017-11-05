package com.ffwatl.admin.catalog.service;

import com.ffwatl.admin.catalog.domain.ProductCategory;
import com.ffwatl.admin.catalog.domain.CommonCategory;
import com.ffwatl.admin.catalog.domain.Product;
import com.ffwatl.admin.catalog.domain.ProductAttribute;
import com.ffwatl.common.persistence.FetchMode;

import java.util.List;

/**
 * @author ffw_ATL.
 */
public interface CatalogService {

    Product createProduct();

    Product saveProduct(Product product);

    Product findProductById(long id, FetchMode fetchMode);

    List<Product> findProductsByName(String name, int start, int maxResult, FetchMode fetchMode);

    List<Product> findProductsByStatus(boolean isActive, FetchMode fetchMode);

    List<Product> findProductsByStatusAndCategory(boolean isActive, ProductCategory productCategory, FetchMode fetchMode);

    List<Product> findAllProducts(FetchMode fetchMode);

    void removeProduct(Product product);


    /* Categories */

    ProductCategory saveCategory(ProductCategory productCategory);

    void saveCategoriesInBulk(List<? extends ProductCategory> list);

    void removeCategory(ProductCategory productCategory);

    ProductCategory findCategoryById(long categoryId, FetchMode fetchMode);

    List<ProductCategory> findAllCategoriesUsed(FetchMode fetchMode);

    List<ProductCategory> findGenderGroups(FetchMode fetchMode);

    List<ProductCategory> findCategoriesByDepthLevel(int level, FetchMode fetchMode);

    List<ProductCategory> findCategoriesByCat(CommonCategory cat, FetchMode fetchMode);

    List<ProductCategory> findCategoriesByDepthLevelAndName(int level, String name, FetchMode fetchMode);

    ProductAttribute findProductAttributeById(long id, FetchMode fetchMode);

}