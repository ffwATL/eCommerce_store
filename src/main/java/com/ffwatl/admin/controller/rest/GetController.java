package com.ffwatl.admin.controller.rest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.ffwatl.admin.dto.ItemGroupDto;
import com.ffwatl.admin.entities.items.CommonCategory;
import com.ffwatl.admin.entities.items.Item;
import com.ffwatl.admin.entities.items.brand.Brand;
import com.ffwatl.admin.entities.items.clothes.size.EuroSize;
import com.ffwatl.admin.entities.items.color.Color;
import com.ffwatl.admin.filter.grid_filter.ClothesGridFilter;
import com.ffwatl.admin.filter.grid_filter.GridFilter;
import com.ffwatl.admin.filter.grid_filter.ItemGridFilter;
import com.ffwatl.admin.presenters.filter.ClothesFilterPresenter;
import com.ffwatl.admin.presenters.items.ItemCatalog;
import com.ffwatl.admin.presenters.items.ItemCatalogPresenter;
import com.ffwatl.admin.presenters.items.ItemPresenter;
import com.ffwatl.admin.presenters.items.update.ItemsExpressInfoPresenter;
import com.ffwatl.admin.presenters.options.ClothesOptionsPresenter;
import com.ffwatl.service.clothes.BrandService;
import com.ffwatl.service.clothes.ClothesPaginationService;
import com.ffwatl.service.group.ItemGroupService;
import com.ffwatl.service.items.ColorService;
import com.ffwatl.service.items.EuroSizeService;
import com.ffwatl.service.items.ItemPaginationServiceImpl;
import com.ffwatl.service.items.ItemService;
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
 */
@RestController
@RequestMapping(value = "/admin/ajax/get", method = RequestMethod.POST)
public class GetController {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ItemGroupService itemGroupService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemPaginationServiceImpl itemPaginationService;
    @Autowired
    private ClothesPaginationService clothesPaginationService;
    @Autowired
    private EuroSizeService euroSizeService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private Settings settings; //contains url for images and directories to save images


    /**
     * Returns ItemGroupDto object with all children by given group name. It's only looking for
     * a result from level 1 of ItemGroup hierarchy;
     * @param name - name of the ItemGroup from level 1 of ItemGroup hierarchy;
     * @return ItemGroupDto object with all children.
     */
    @RequestMapping(value = "/itemgroup")
    @ResponseBody
    public ResponseEntity<ItemGroupDto> ajaxAllItemGroupByName(@RequestParam String name){
        ItemGroupDto result = itemGroupService.findByLvlAndByNameFetchCollection(1, name);
        return ResponseEntity.ok(result);
    }

    /**
     * Returns List of all EuroSize objects that corresponds given CommonCategory 'cat' parameter.
     * If parameter 'cat' is empty, method returns status 400;
     * @param cat CommonCategory 'cat' parameter. Shouldn't be null;
     * @return List of all EuroSize objects that corresponds given CommonCategory 'cat' parameter.
     */
    @RequestMapping(value = "/eurosize/cat")
    @ResponseBody
    public ResponseEntity<List<EuroSize>> ajaxEuroSizeByCat(@RequestParam String cat){
        try {
            return ResponseEntity.ok(euroSizeService.findByCat(CommonCategory.valueOf(cat)));
        }catch (Exception e){
            logger.error("Error on getting EuroSize by cat. " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/filter/clothes")
    @ResponseBody
    public ResponseEntity<ClothesFilterPresenter> ajaxClothesFilters(){
        ClothesFilterPresenter result = new ClothesFilterPresenter();
        result.setBrandList(brandService.findAllUsed());
        result.setSize(euroSizeService.findAllUsed());
        result.setUsedCat(itemGroupService.findAllUsedWrapper());
        result.setGender(itemGroupService.findGenderGroup());
        result.setColors(colorService.findAllUsed());
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/color/all")
    @ResponseBody
    public ResponseEntity<List<Color>> ajaxAllColor(){
        return ResponseEntity.ok(colorService.findAll());
    }

    @RequestMapping(value = "/brand/all")
    @ResponseBody
    public ResponseEntity<List<Brand>> ajaxAllBrands(){
        return ResponseEntity.ok( brandService.findAll());
    }

    @RequestMapping(value = "/item/all")
    @ResponseBody
    public ResponseEntity<ItemCatalogPresenter> ajaxAllItems(@RequestParam Map<String, String> params){
        Page<? extends Item> page = getPage(params.get("cat"), params);
        try {
            ItemCatalogPresenter presenter = new ItemCatalogPresenter(fillItemCatalog((List<Item>) page.getContent()));
            presenter.setPge(page.getNumber());
            presenter.setPgeSize(page.getNumberOfElements());
            presenter.setTotalPages(page.getTotalPages());
            presenter.setTotalItems(page.getTotalElements());
            return ResponseEntity.ok(presenter);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/expressEditInfo")
    @ResponseBody
    public ResponseEntity<ItemsExpressInfoPresenter> ajaxExpressEdit(@RequestParam CommonCategory cat){
        return ResponseEntity.ok(new ItemsExpressInfoPresenter()
                        .setColor(colorService.findAll())
                        .setItemGroup(itemGroupService.findByCatNoChildren(cat))
        );
    }

    @RequestMapping(value = "/item/single")
    @ResponseBody
    public ResponseEntity<ItemPresenter> ajaxSingleItem(@RequestParam long id){
        return ResponseEntity.ok(itemService.findItemPresenterById(id));
    }

    @RequestMapping(value = "/item/options/clothes")
    @ResponseBody
    public ResponseEntity<ClothesOptionsPresenter> ajaxClothesOptions() throws JsonProcessingException {
        ClothesOptionsPresenter presenter = new ClothesOptionsPresenter();
        presenter.setItemGroup(itemGroupService.findByLvlAndByNameFetchCollection(1, "Clothes"));
        presenter.setBrandList(brandService.findAll());
        presenter.setColorList(colorService.findAll());
        presenter.setBrandImgUrl(settings.getBrandImgUrl());
        return ResponseEntity.ok(presenter);
    }

    private Page<? extends Item> getPage(String cat, Map<String, String> params){
        GridFilter filter;
        if(cat == null) filter = new ItemGridFilter(params);
        else if(cat.equals("Clothes") || cat.equals("Одежда")){
            logger.info("params: " + params);
            filter = new ClothesGridFilter(params);
            return clothesPaginationService.findAllByFilter(filter);
        }else filter = new ItemGridFilter(params);
        return itemPaginationService.findAll(filter);
    }

    private List<ItemCatalog> fillItemCatalog(List<Item> items){
        List<ItemCatalog> itemCatalogList = new ArrayList<>(items.size());
        for(Item i: items){
            ItemCatalog catalog = new ItemCatalog(i, settings.getPhotoUrl());
            itemCatalogList.add(catalog);
        }
        return itemCatalogList;
    }
}