package com.ffwatl.admin.catalog.service;

import com.ffwatl.admin.catalog.domain.Category;
import com.ffwatl.admin.catalog.domain.CommonCategory;
import com.ffwatl.admin.catalog.domain.Product;
import com.ffwatl.common.persistence.FetchMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ffw_ATL.
 */
@Service("catalog_service")
@Transactional(readOnly = true)
public class CatalogServiceImpl implements CatalogService {



    @Override
    public Product createProduct() {
        return null;
    }

    @Override
    public Product saveProduct(Product product) {
        return null;
    }

    @Override
    public Product findProductById(long id, FetchMode fetchMode) {
        return null;
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
    public List<Product> findProductsByStatusAndCategory(boolean isActive, Category category, FetchMode fetchMode) {
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
    public Category saveCategory(Category category) {
        return null;
    }

    @Override
    public void saveCategoriesInBulk(List<? extends Category> list) {

    }

    @Override
    public void removeCategory(Category category) {

    }

    @Override
    public Category findCategoryById(long categoryId, FetchMode fetchMode) {
        return null;
    }

    @Override
    public List<Category> findAllCategoriesUsed(FetchMode fetchMode) {
        return null;
    }

    @Override
    public List<Category> findGenderGroups(FetchMode fetchMode) {
        return null;
    }

    @Override
    public List<Category> findCategoriesByDepthLevel(int level, FetchMode fetchMode) {
        return null;
    }

    @Override
    public List<Category> findCategoriesByCat(CommonCategory cat, FetchMode fetchMode) {
        return null;
    }

    @Override
    public List<Category> findCategoriesByDepthLevelAndName(int level, String name, FetchMode fetchMode) {
        return null;
    }
}