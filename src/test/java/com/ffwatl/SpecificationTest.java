package com.ffwatl;


import com.ffwatl.controller.AjaxController;
import com.ffwatl.dao.items.ClothesItemRepository;
import com.ffwatl.dao.items.ItemRepository;
import com.ffwatl.domain.filter.grid_filter.ClothesGridFilter;
import com.ffwatl.domain.filter.grid_filter.ItemGridFilter;
import com.ffwatl.domain.group.ItemGroup;
import com.ffwatl.domain.group.wrap.GroupWrapper;
import com.ffwatl.domain.items.CommonCategory;
import com.ffwatl.domain.items.Item;
import com.ffwatl.service.clothes.ClothesPaginationService;
import com.ffwatl.service.group.ItemGroupService;
import com.ffwatl.service.items.ItemPaginationServiceImpl;
import com.ffwatl.service.items.ItemService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@TransactionConfiguration(defaultRollback = true)
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
    private AjaxController ajaxController;

    @Autowired
    private ItemGroupService itemGroupService;

    @Autowired
    private ClothesPaginationService clothesPaginationService;

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

    private GroupWrapper resolveItemGroup(ItemGroup itemGroup, List<GroupWrapper> all, List<GroupWrapper> gender){
        if(itemGroup.getLevel() == 2){
            gender.add(new GroupWrapper()
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
        return new GroupWrapper()
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
        ItemGroup itemGroup = itemGroupService.findByLvlAndByNameNoLazy(1, "Clothes");
        List<GroupWrapper> gender = new ArrayList<>();
        List<GroupWrapper> list = new ArrayList<>();
        resolveItemGroup(itemGroup, list, gender);
        System.err.println("result: " + list);
        System.err.println("*** gender: " + gender);

    }*/

   /* @Test
    public void imageReadTest() throws IOException {
        ajaxController.fillWrapper(itemService.findAll());
    }*/

    @Test
    @Ignore
    public void test1(){
        String t = "black|red";
        for(String s: t.split("\\|")){
            System.err.println("s: "+ s);
        }
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