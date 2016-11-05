package com.ffwatl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffwatl.admin.entities.group.IGroup;
import com.ffwatl.admin.entities.items.CommonCategory;
import com.ffwatl.admin.entities.items.DefaultItem;
import com.ffwatl.admin.filter.grid_filter.ClothesGridFilter;
import com.ffwatl.admin.filter.grid_filter.ItemGridFilter;
import com.ffwatl.admin.presenters.itemgroup.ItemGroupPresenter;
import com.ffwatl.admin.presenters.items.ClothesItemPresenter;
import com.ffwatl.admin.presenters.options.ClothesOptionsPresenter;
import com.ffwatl.dao.items.ClothesItemRepository;
import com.ffwatl.dao.items.ItemRepository;
import com.ffwatl.service.clothes.BrandService;
import com.ffwatl.service.clothes.ClothesItemService;
import com.ffwatl.service.clothes.ClothesPaginationService;
import com.ffwatl.service.group.ItemGroupService;
import com.ffwatl.service.items.ColorService;
import com.ffwatl.service.items.EuroSizeService;
import com.ffwatl.service.items.ItemPaginationServiceImpl;
import com.ffwatl.service.items.ItemService;
import com.ffwatl.util.Settings;
import com.ffwatl.util.WebUtil;
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

import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Rollback(value = false)
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

    private IGroup resolveItemGroup(IGroup itemGroup, List<IGroup> all, List<IGroup> gender){
        if(itemGroup.getLevel() == 2){
            gender.add(new ItemGroupPresenter(itemGroup, false));
        }
        if(itemGroup.getChild() != null && itemGroup.getChild().size() > 0){
            for(IGroup i: itemGroup.getChild()) {
                if(i.getCat() != CommonCategory.NONE) all.add(resolveItemGroup(i, all, gender));
                else resolveItemGroup(i, all, gender);
            }
        }
        return new ItemGroupPresenter()
                .setCat(itemGroup.getCat())
                .setGroupName(itemGroup.getGroupName())
                .setId(itemGroup.getId())
                .setLevel(itemGroup.getLevel());
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

    public Optional<List<ClothesItemPresenter>> importItemsFromJsonUTF8(@NotNull File file) throws IOException {
        if(!file.exists() || !file.getName().endsWith(".json")) throw new IllegalArgumentException("Wrong input file");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF8"))){
            char[] buf = new char[(int) file.length()];
            in.read(buf);
            String json = new String(buf);
            ObjectMapper mapper = new ObjectMapper();
            return Optional.of(mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(
                    List.class, ClothesItemPresenter.class)));
        }catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public void exportItemsToJsonUTF8(@NotNull File output){
        List<ClothesItemPresenter> items = clothesItemService.findAll().get();
        try(OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(output), "UTF-8")){
            writer.write(new ObjectMapper().writeValueAsString(items));
            writer.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Test
    @Ignore
    public void importItems() throws IOException {
        clothesItemService.save(WebUtil.importItemsFromJsonUTF8(new File("items.json")));
    }

    @Test
    @Ignore
    public void readItemsToJson() throws IOException {
        File file = new File("items_export.json");
        if(!file.exists() || !file.getName().endsWith(".json")) throw new IllegalArgumentException("Wrong input file");
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF8"));
        char[] buf = new char[(int) file.length()];
        in.read(buf);
        String json = new String(buf);
        ObjectMapper mapper = new ObjectMapper();
        List<ClothesItemPresenter> items = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(
                List.class, ClothesItemPresenter.class));
        System.err.println(items);
       /* List<ClothesItemPresenter> items = clothesItemService.findAll().get();
        try(OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("items_export.json"), "UTF-8")){
            writer.write(new ObjectMapper().writeValueAsString(items));
            writer.flush();
        }catch (IOException e){
            e.printStackTrace();
        }*/
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
        Page<? extends DefaultItem> page = clothesPaginationService.findAllByFilter(cfc);
        for(DefaultItem c: page.getContent()){
            System.err.println("id: " + c.getId() + ", name: " + c.getItemName());
        }
        System.err.println("*** size: " + page.getNumberOfElements()+", total pages: "+page.getTotalPages());
    }
}