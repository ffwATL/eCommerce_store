package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.domain.*;
import com.ffwatl.admin.catalog.domain.ProductDefault;
import com.ffwatl.admin.catalog.domain.ProductClothes;
import com.ffwatl.admin.catalog.domain.presenter.ProductImage;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.admin.catalog.domain.presenter.ClothesItemPresenter;
import com.ffwatl.admin.catalog.domain.presenter.ProductUpdateImpl;
import com.ffwatl.admin.catalog.domain.presenter.ItemUpdatePresenter;
import com.ffwatl.admin.user.domain.dto.UserDTO;
import com.ffwatl.admin.catalog.dao.ItemDao;
import com.ffwatl.util.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService{

    @Autowired
    private ItemDao itemDao;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private EuroSizeService euroSizeService;
    @Autowired
    private ItemGroupService itemGroupService;
    @Autowired
    private Settings settings;

    @Override
    public ProductDefault findById(long id) {
        ProductDefault item = itemDao.findById(id);
        item.getCategory().setChild(null);
        return item;
    }

    @Override
    public List<ProductDefault> findAll() {
        return itemDao.findAll();
    }

    @Override
    @Transactional
    public void save(ProductDefault item) {
        item.setColor(colorService.findById(item.getColor().getId()));
        item.setItemGroup(itemGroupService.findById(item.getCategory().getId()));
        if(item instanceof ProductClothes){
            ProductClothes i = (ProductClothes) item;
            i.setBrand(brandService.findById(i.getBrand().getId()));
            for (Size s: i.getSize()){
                s.setEu_size(euroSizeService.findById(s.getEu_size().getId()));
            }
        }
        itemDao.save(item);
    }

    @Override
    @Transactional
    public void remove(ProductDefault item) {
        itemDao.remove(item);
    }

    @Override
    @Transactional
    public void changeItemStatus(ProductDefault item){
        ProductDefault item_1 = findById(item.getId());
        item_1.setActive(item.isActive());
    }

    @Override
    @Transactional
    public void updateSingleItem(ItemUpdatePresenter update){
        Product freshItem = update.getItem();
        ProductDefault item = findById(freshItem.getId());
        if(item == null) {
            throw new IllegalArgumentException("Probably wrong Product id. Product not found :( [id]="+freshItem.getId() );
        }
        item.setItemGroup(itemGroupService.findById(freshItem.getCategory().getId()));
        item.setColor(colorService.findById(freshItem.getColor().getId()));
        item.setItemName(freshItem.getProductName());
        item.setActive(freshItem.isActive());
        item.setSalePrice(freshItem.getSalePrice());
        item.setDiscount(freshItem.getDiscount());
        item.setLastChangeDate(new Timestamp(System.currentTimeMillis()));
    }

    @Override
    @Transactional
    public void updateItems(ItemUpdatePresenter update) {
        Map<String, String> map = update.getOptions();
        long[] ids = update.getIdentifiers();
        int priceValue = map.get("priceValue") != null ? Integer.valueOf(map.get("priceValue")) : 0;
        int discount = map.get("discount") != null ? Integer.valueOf(map.get("discount")) : -1;
        for (long id: ids){
            ProductDefault item = findById(id);
            item.setSalePrice(item.getSalePrice() + priceValue);
            if(discount > -1) item.setDiscount(discount);
            if(map.get("isActive") != null) item.setActive(Boolean.valueOf(map.get("isActive")));
            item.setLastChangeDate(new Timestamp(System.currentTimeMillis()));
        }
    }

    @Override
    public ProductUpdateImpl findItemPresenterById(long id) {
        ProductDefault item = itemDao.findById(id);
        if(item == null){
            System.err.println("Product == null");
            throw new IllegalArgumentException();
        }
        item.getCategory().setChild(null);
        ProductUpdateImpl presenter = item2Presenter(item);

        if(item instanceof ProductClothes){
            ClothesItemPresenter cPresenter = new ClothesItemPresenter(presenter);
            cPresenter.setBrand(((ProductClothes) item).getBrand());
            Collections.sort(((ProductClothes) item).getSize());
            cPresenter.setSize(((ProductClothes) item).getSize());
            cPresenter.setBrandImgUrl(settings.getBrandImgUrl());
            return cPresenter;
        }
        return presenter;
    }

    private UserDTO user2Presenter(User u){
        if(u == null) return null;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(u.getId());
        userDTO.setFirstName(u.getFirstName());
        userDTO.setEmail(u.getEmail());
        userDTO.setLastName(u.getLastName());
        userDTO.setCreateDt(u.getCreateDt());
        userDTO.setPhotoUrl(u.getPhotoUrl());
        userDTO.setState(u.getState());
        return userDTO;
    }

    private ProductUpdateImpl item2Presenter(ProductDefault item){
        String photoDir = settings.getPhotoDir()+"item_"+item.getId();
        String url = settings.getPhotoUrl() +"item_"+item.getId()+"/";

        ProductUpdateImpl presenter = new ProductUpdateImpl();

        presenter.setAddedBy(user2Presenter(item.getAddedBy()));
        presenter.setId(item.getId());
        presenter.setDiscount(item.getDiscount());
        presenter.setItemGroup(item.getCategory());
        presenter.setSalePrice(item.getSalePrice());
        presenter.setActive(item.isActive());
        presenter.setRetailPrice(item.getRetailPrice());
        presenter.setColor(item.getColor());
        presenter.setItemName(item.getProductName());
        presenter.setQuantity(item.getQuantity());
        presenter.setImages(urlImages(photoDir, "xl_.jpg", url));
        presenter.setThumbs(urlImages(photoDir, "s.jpg", url));
        presenter.setDescription(item.getDescription());
        presenter.setExtraNotes(item.getExtraNotes());
        presenter.setGender(item.getGender());
        return presenter;
    }

    private List<ProductImage> urlImages(String directory, String end, String url) {
        List<ProductImage> textFiles = new ArrayList<>();
        for (File file : new File(directory).listFiles()) {
            if (file.getName().endsWith(end)) {
                ProductImage image = new ProductImage();
                image.setSize(file.length());
                image.setName(file.getName());
                image.setUrl(url+file.getName());
                textFiles.add(image);
            }
        }
        return textFiles;
    }
}