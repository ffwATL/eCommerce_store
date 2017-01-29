package com.ffwatl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffwatl.admin.catalog.domain.Category;
import com.ffwatl.admin.catalog.domain.CommonCategory;
import com.ffwatl.admin.catalog.domain.ProductDefault;
import com.ffwatl.admin.catalog.domain.filter.grid_filter.ClothesGridFilter;
import com.ffwatl.admin.catalog.domain.filter.grid_filter.ItemGridFilter;
import com.ffwatl.admin.catalog.domain.dto.CategoryDTO;
import com.ffwatl.admin.catalog.domain.presenter.ClothesItemPresenter;
import com.ffwatl.admin.catalog.domain.presenter.ClothesOptionsPresenter;
import com.ffwatl.admin.catalog.dao.ClothesItemRepository;
import com.ffwatl.admin.catalog.dao.ItemRepository;
import com.ffwatl.admin.catalog.service.BrandService;
import com.ffwatl.admin.catalog.service.ClothesItemService;
import com.ffwatl.admin.catalog.service.ClothesPaginationService;
import com.ffwatl.admin.catalog.service.ItemGroupService;
import com.ffwatl.admin.catalog.service.ColorService;
import com.ffwatl.admin.catalog.service.EuroSizeService;
import com.ffwatl.admin.catalog.service.ItemPaginationServiceImpl;
import com.ffwatl.admin.catalog.service.ItemService;
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

    private Category resolveItemGroup(Category itemGroup, List<Category> all, List<Category> gender){
        if(itemGroup.getLevel() == 2){
            gender.add(new CategoryDTO(itemGroup, false));
        }
        if(itemGroup.getChild() != null && itemGroup.getChild().size() > 0){
            for(Category i: itemGroup.getChild()) {
                if(i.getCat() != CommonCategory.NONE) all.add(resolveItemGroup(i, all, gender));
                else resolveItemGroup(i, all, gender);
            }
        }
        return new CategoryDTO()
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
        Product item = new Product();
        Product clothesItem = new ProductClothes();
        System.err.println(clothesItem instanceof ProductClothes);
    }*/

   /* @Test
    public void lazyInitTest(){
        CategoryImpl itemGroup = itemGroupService.findByLvlAndByNameFetchCollection(1, "Clothes");
        List<CategoryDTO> gender = new ArrayList<>();
        List<CategoryDTO> list = new ArrayList<>();
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
        Page<? extends ProductDefault> page = clothesPaginationService.findAllByFilter(cfc);
        for(ProductDefault c: page.getContent()){
            System.err.println("id: " + c.getId() + ", name: " + c.getProductName());
        }
        System.err.println("*** size: " + page.getNumberOfElements()+", total pages: "+page.getTotalPages());
    }
}