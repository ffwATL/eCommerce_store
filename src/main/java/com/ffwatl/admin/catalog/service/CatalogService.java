package com.ffwatl.admin.catalog.service;

import com.ffwatl.admin.catalog.domain.Category;
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

    List<Product> findProductsByStatusAndCategory(boolean isActive, Category category, FetchMode fetchMode);

    List<Product> findAllProducts(FetchMode fetchMode);

    void removeProduct(Product product);


    /* Categories */

    Category saveCategory(Category category);

    void saveCategoriesInBulk(List<? extends Category> list);

    void removeCategory(Category category);

    Category findCategoryById(long categoryId, FetchMode fetchMode);

    List<Category> findAllCategoriesUsed(FetchMode fetchMode);

    List<Category> findGenderGroups(FetchMode fetchMode);

    List<Category> findCategoriesByDepthLevel(int level, FetchMode fetchMode);

    List<Category> findCategoriesByCat(CommonCategory cat, FetchMode fetchMode);

    List<Category> findCategoriesByDepthLevelAndName(int level, String name, FetchMode fetchMode);

    ProductAttribute findProductAttributeById(long id, FetchMode fetchMode);

}