package com.ffwatl.manage.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.ffwatl.manage.dto.ItemGroupDto;
import com.ffwatl.manage.entities.filter.grid_filter.ClothesGridFilter;
import com.ffwatl.manage.entities.filter.grid_filter.GridFilter;
import com.ffwatl.manage.entities.filter.grid_filter.ItemGridFilter;
import com.ffwatl.manage.entities.items.CommonCategory;
import com.ffwatl.manage.entities.items.Item;
import com.ffwatl.manage.entities.items.brand.Brand;
import com.ffwatl.manage.entities.items.clothes.size.EuroSize;
import com.ffwatl.manage.entities.items.color.Color;
import com.ffwatl.manage.presenters.filter.ClothesFilterPresenter;
import com.ffwatl.manage.presenters.items.ItemCatalog;
import com.ffwatl.manage.presenters.items.ItemCatalogPresenter;
import com.ffwatl.manage.presenters.items.ItemPresenter;
import com.ffwatl.manage.presenters.items.update.ItemUpdatePresenter;
import com.ffwatl.manage.presenters.items.update.ItemsExpressInfoPresenter;
import com.ffwatl.manage.presenters.options.ClothesOptionsPresenter;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class AjaxController {

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
    private Settings settings;

    @RequestMapping(value = "/manage/ajax/get/itemgroup", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ItemGroupDto> ajaxAllItemGroupByParentName(@RequestParam String name){
        ItemGroupDto result = itemGroupService.findByLvlAndByNameFetchCollection(1, name);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/manage/ajax/get/eurosize/cat", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<EuroSize>> ajaxEuroSizeByCat(@RequestParam String cat){
        if(cat == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.ok(euroSizeService.findByCat(CommonCategory.valueOf(cat)));
    }

    @RequestMapping(value = "/manage/ajax/update/item/single", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> updateSingleItem(@RequestBody ItemUpdatePresenter update){
        itemService.updateSingleItem(update);
        return ResponseEntity.ok("Ok");
    }

    @RequestMapping(value = "/manage/ajax/update/item/multi", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> updateItems(@RequestBody ItemUpdatePresenter update){
        itemService.updateItems(update);
        return ResponseEntity.ok("ok");
    }

    @RequestMapping(value = "/manage/ajax/get/filter/clothes", method = RequestMethod.POST)
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

    @RequestMapping(value = "/manage/ajax/get/color/all", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<Color>> ajaxAllColor(){
        return ResponseEntity.ok(colorService.findAll());
    }

    @RequestMapping(value = "/manage/ajax/get/brand/all", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<Brand>> ajaxAllBrands(){
        return ResponseEntity.ok( brandService.findAll());
    }

    @RequestMapping(value = "/manage/ajax/get/item/all" ,method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ItemCatalogPresenter> ajaxAllItems(@RequestParam Map<String, String> params){
        Page<? extends Item> page = getPage(params.get("cat"), params);
        try {
            ItemCatalogPresenter holder = new ItemCatalogPresenter(fillWrapper((List<Item>) page.getContent()));
            holder.setPge(page.getNumber());
            holder.setPgeSize(page.getNumberOfElements());
            holder.setTotalPages(page.getTotalPages());
            holder.setTotalItems(page.getTotalElements());
            return ResponseEntity.ok(holder);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/manage/ajax/get/expressEditInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ItemsExpressInfoPresenter> ajaxExpressEdit(@RequestParam CommonCategory cat){
        return ResponseEntity.ok(new ItemsExpressInfoPresenter()
                .setColor(colorService.findAll())
                .setItemGroup(itemGroupService.findByCatNoChildren(cat))
        );
    }

    @RequestMapping(value = "/manage/ajax/get/item/single", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ItemPresenter> ajaxSingleItem(@RequestParam long id){
        return ResponseEntity.ok(itemService.findItemPresenterById(id));
    }

    @RequestMapping(value = "/manage/ajax/get/item/options/clothes", method = RequestMethod.POST)
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

    private List<ItemCatalog> fillWrapper(List<Item> items){
        List<ItemCatalog> wrapperList = new ArrayList<>(items.size());
        for(Item i: items){
            ItemCatalog wrapper = new ItemCatalog(i, settings.getPhotoUrl());
            wrapperList.add(wrapper);
        }
        return wrapperList;
    }
}