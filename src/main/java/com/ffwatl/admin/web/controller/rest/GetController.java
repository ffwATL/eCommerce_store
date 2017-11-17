package com.ffwatl.admin.web.controller.rest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.ffwatl.admin.catalog.domain.*;
import com.ffwatl.admin.catalog.domain.filter.grid_filter.ClothesGridFilter;
import com.ffwatl.admin.catalog.domain.filter.grid_filter.GridFilter;
import com.ffwatl.admin.catalog.domain.filter.grid_filter.ItemGridFilter;
import com.ffwatl.admin.catalog.domain.response.*;
import com.ffwatl.admin.catalog.service.*;
import com.ffwatl.util.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Rest controller, serves all the ajax requests that starts with '/admin/ajax/get' and have 'POST' type.
 * Deprecated! This controller will be removed soon, since we've moved to REST API
 */
@RestController
@RequestMapping(value = "/admin/ajax/get", method = RequestMethod.POST)
@Deprecated
public class GetController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ItemPaginationServiceImpl itemPaginationService;

    @Autowired
    private Settings settings; //contains url for images and directories to save images


    /**
     * Returns ItemGroupDTO object with all children by given group name. It's only looking for
     * a result from level 1 of ProductCategoryImpl hierarchy;
     * @param name - name of the ProductCategoryImpl from level 1 of ProductCategoryImpl hierarchy;
     * @return ItemGroupDTO object with all children.
     */
    @RequestMapping(value = "/itemgroup")
    @ResponseBody
    public ResponseEntity<ProductCategory> ajaxAllItemGroupByName(@RequestParam String name){
        ProductCategory result = productCategoryService.findByLvlAndByName(1, name, null);
        return ResponseEntity.ok(result);
    }

    /**
     * Returns List of all ProductAttributeType objects that corresponds given CommonCategory 'cat' parameter.
     * If parameter 'cat' is empty, method returns status 400;
     * @param cat CommonCategory 'cat' parameter. Shouldn't be null;
     * @return List of all ProductAttributeType objects that corresponds given CommonCategory 'cat' parameter.
     */
    @RequestMapping(value = "/eurosize/cat")
    @ResponseBody
    public ResponseEntity<List<ProductAttributeType>> ajaxEuroSizeByCat(@RequestParam String cat){
        try {
            return ResponseEntity.ok(new ArrayList<>()/*euroSizeService.findByCat(CommonCategory.valueOf(cat))*/);
        }catch (Exception e){
            LOGGER.error("Error on getting ProductAttributeType by cat. " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/filter/clothes")
    @ResponseBody
    public ResponseEntity<FilterProductClothes> ajaxClothesFilters(){
        FilterProductClothes result = new FilterProductClothes();
        result.setBrandList(brandService.findAllUsed());
       /* result.setSize(euroSizeService.findAllUsed());*/

        result.setGender(null);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/brand/all")
    @ResponseBody
    public ResponseEntity<List<Brand>> ajaxAllBrands(){
        return ResponseEntity.ok( brandService.findAll());
    }

    @RequestMapping(value = "/item/all")
    @ResponseBody
    public ResponseEntity<CatalogImpl> ajaxAllItems(@RequestParam Map<String, String> params){
        Page<? extends ProductImpl> page = getPage(params.get("cat"), params);
        try {
            CatalogImpl presenter = new CatalogImpl(fillItemCatalog((List<ProductImpl>) page.getContent()));
            presenter.setPge(page.getNumber());
            presenter.setPgeSize(page.getNumberOfElements());
            presenter.setTotalPages(page.getTotalPages());
            presenter.setTotalItems(page.getTotalElements());
            return ResponseEntity.ok(presenter);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/expressEditInfo")
    @ResponseBody
    public ResponseEntity<ItemsExpressInfoPresenter> ajaxExpressEdit(@RequestParam CommonCategory cat){
        return ResponseEntity.ok(new ItemsExpressInfoPresenter()
                        .setItemGroup(null)
        );
    }

    @RequestMapping(value = "/item/single")
    @ResponseBody
    public ResponseEntity<ProductUpdateImpl> ajaxSingleItem(@RequestParam long id){
        return ResponseEntity.ok(productService.findItemPresenterById(id));
    }

    @RequestMapping(value = "/item/options/clothes")
    @ResponseBody
    public ResponseEntity<ClothesOptionsPresenter> ajaxClothesOptions() throws JsonProcessingException {
        ClothesOptionsPresenter presenter = new ClothesOptionsPresenter();

        presenter.setItemGroup(productCategoryService.findByLvlAndByName(1, "Clothes", null));
        presenter.setBrandList(brandService.findAll());
        presenter.setBrandImgUrl(settings.getBrandImgUrl());

        return ResponseEntity.ok(presenter);
    }

    private Page<? extends ProductImpl> getPage(String cat, Map<String, String> params){
        GridFilter filter;
        if(cat == null) filter = new ItemGridFilter(params);
        else if(cat.equals("Clothes") || cat.equals("Одежда")){
            LOGGER.info("params: " + params);
            filter = new ClothesGridFilter(params);
            return /*clothesPaginationService.findAllByFilter(filter)*/ null;
        }else filter = new ItemGridFilter(params);
        return itemPaginationService.findAll(filter);
    }

    private List<CatalogItemImpl> fillItemCatalog(List<ProductImpl> items){
        List<CatalogItemImpl> itemCatalogList = new ArrayList<>(items.size());
        for(ProductImpl i: items){
            CatalogItemImpl catalog = new CatalogItemImpl(i, settings.getPhotoUrl());
            itemCatalogList.add(catalog);
        }
        return itemCatalogList;
    }
}