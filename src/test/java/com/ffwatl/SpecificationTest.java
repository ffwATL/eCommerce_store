package com.ffwatl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffwatl.dao.items.ClothesItemRepository;
import com.ffwatl.dao.items.ItemRepository;
import com.ffwatl.manage.entities.group.ItemGroup;
import com.ffwatl.manage.entities.items.CommonCategory;
import com.ffwatl.manage.entities.items.Item;
import com.ffwatl.manage.filter.grid_filter.ClothesGridFilter;
import com.ffwatl.manage.filter.grid_filter.ItemGridFilter;
import com.ffwatl.manage.presenters.itemgroup.ItemGroupPresenter;
import com.ffwatl.manage.presenters.options.ClothesOptionsPresenter;
import com.ffwatl.service.clothes.BrandService;
import com.ffwatl.service.clothes.ClothesItemService;
import com.ffwatl.service.clothes.ClothesPaginationService;
import com.ffwatl.service.group.ItemGroupService;
import com.ffwatl.service.items.ColorService;
import com.ffwatl.service.items.EuroSizeService;
import com.ffwatl.service.items.ItemPaginationServiceImpl;
import com.ffwatl.service.items.ItemService;
import com.ffwatl.util.Settings;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Rollback(value = true)
@ContextConfiguration({"/spring/application-config.xml", "/spring/spring-security.xml" })
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class SpecificationTest {

    @Autowired
    private ClothesItemRepository clothesItemRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemPaginationServiceImpl itemPaginationService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private EuroSizeService euroSizeService;

    @Autowired
    private ItemGroupService itemGroupService;
    @Autowired
    private ClothesItemService clothesItemService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ClothesPaginationService clothesPaginationService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private Settings settings;

    private Map<String, String> params;

    {
        params = new HashMap<>();
        params.put("pge","0");
        params.put("pgeSize","4");
        /*params.put("salePrice", "0|1000");
        params.put("originPrice","0|1000");*/
        params.put("itemGroup", "13");
        /*params.put("parentGroup","Clothes");*/
    }

    private ItemGroupPresenter resolveItemGroup(ItemGroup itemGroup, List<ItemGroupPresenter> all, List<ItemGroupPresenter> gender){
        if(itemGroup.getLevel() == 2){
            gender.add(new ItemGroupPresenter()
                    .setGroupName(itemGroup.getGroupName())
                    .setId(itemGroup.getId())
                    .setLvl(itemGroup.getLevel())
                    .setCat(itemGroup.getCat()));
        }
        if(itemGroup.getChild() != null && itemGroup.getChild().size() > 0){
            for(ItemGroup i: itemGroup.getChild()) {
                if(i.getCat() != CommonCategory.NONE) all.add(resolveItemGroup(i, all, gender));
                else resolveItemGroup(i, all, gender);
            }
        }
        return new ItemGroupPresenter()
                .setCat(itemGroup.getCat())
                .setGroupName(itemGroup.getGroupName())
                .setId(itemGroup.getId())
                .setLvl(itemGroup.getLevel());
    }
   /* @Test
    public void imageTest() throws IOException {
        File file = new File("c:/test 1/favicon.png");
        System.err.println(file.getAbsolutePath());
    }*/
    /*@Test
    public void instanceTest(){
        Item item = new Item();
        Item clothesItem = new ClothesItem();
        System.err.println(clothesItem instanceof ClothesItem);
    }*/

   /* @Test
    public void lazyInitTest(){
        ItemGroup itemGroup = itemGroupService.findByLvlAndByNameFetchCollection(1, "Clothes");
        List<ItemGroupPresenter> gender = new ArrayList<>();
        List<ItemGroupPresenter> list = new ArrayList<>();
        resolveItemGroup(itemGroup, list, gender);
        System.err.println("result: " + list);
        System.err.println("*** gender: " + gender);

    }*/

    @Test
    @Ignore
    public void imageReadTest(){
        System.err.println("********" + itemService.findItemPresenterById(5));
    }

    @Test
    @Ignore
    public void test1() throws JsonProcessingException {
        ClothesOptionsPresenter presenter = new ClothesOptionsPresenter();
        ObjectMapper mapper = new ObjectMapper();
        presenter.setItemGroup(itemGroupService.findByLvlAndByNameFetchCollection(1, "Clothes"));
        presenter.setBrandList(brandService.findAll());
        presenter.setColorList(colorService.findAll());
        presenter.setBrandImgUrl(settings.getBrandImgUrl());
        System.err.println("*****"+mapper.writeValueAsString(presenter));
    }

    @Test
    @Ignore
    public void workingSpecTest(){
        ItemGridFilter cfc= new ClothesGridFilter(params);
        Page<? extends Item> page = clothesPaginationService.findAllByFilter(cfc);
        for(Item c: page.getContent()){
            System.err.println("id: " + c.getId() + ", name: " + c.getItemName());
        }
        System.err.println("*** size: " + page.getNumberOfElements()+", total pages: "+page.getTotalPages());
    }
}