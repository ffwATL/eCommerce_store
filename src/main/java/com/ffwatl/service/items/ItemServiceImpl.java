package com.ffwatl.service.items;


import com.ffwatl.admin.entities.items.DefaultItem;
import com.ffwatl.admin.entities.items.Item;
import com.ffwatl.admin.entities.items.clothes.ClothesItem;
import com.ffwatl.admin.entities.items.clothes.size.Size;
import com.ffwatl.admin.entities.users.IUser;
import com.ffwatl.admin.presenters.items.ClothesItemPresenter;
import com.ffwatl.admin.presenters.items.ItemImage;
import com.ffwatl.admin.presenters.items.ItemPresenter;
import com.ffwatl.admin.presenters.items.update.ItemUpdatePresenter;
import com.ffwatl.admin.presenters.users.UserGenPresenter;
import com.ffwatl.dao.items.ItemDao;
import com.ffwatl.service.clothes.BrandService;
import com.ffwatl.service.group.ItemGroupService;
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
    public DefaultItem findById(long id) {
        DefaultItem item = itemDao.findById(id);
        item.getItemGroup().setChild(null);
        return item;
    }

    @Override
    public List<DefaultItem> findAll() {
        return itemDao.findAll();
    }

    @Override
    @Transactional
    public void save(DefaultItem item) {
        item.setColor(colorService.findById(item.getColor().getId()));
        item.setItemGroup(itemGroupService.findById(item.getItemGroup().getId()));
        if(item instanceof ClothesItem){
            ClothesItem i = (ClothesItem) item;
            i.setBrand(brandService.findById(i.getBrand().getId()));
            for (Size s: i.getSize()){
                s.setEu_size(euroSizeService.findById(s.getEu_size().getId()));
            }
        }
        itemDao.save(item);
    }

    @Override
    @Transactional
    public void remove(DefaultItem item) {
        itemDao.remove(item);
    }

    @Override
    @Transactional
    public void changeItemStatus(DefaultItem item){
        DefaultItem item_1 = findById(item.getId());
        item_1.setIsActive(item.isActive());
    }

    @Override
    @Transactional
    public void updateSingleItem(ItemUpdatePresenter update){
        Item freshItem = update.getItem();
        DefaultItem item = findById(freshItem.getId());
        if(item == null) {
            throw new IllegalArgumentException("Probably wrong Item id. Item not found :( [id]="+freshItem.getId() );
        }
        item.setItemGroup(itemGroupService.findById(freshItem.getItemGroup().getId()));
        item.setColor(colorService.findById(freshItem.getColor().getId()));
        item.setItemName(freshItem.getItemName());
        item.setIsActive(freshItem.isActive());
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
            DefaultItem item = findById(id);
            item.setSalePrice(item.getSalePrice() + priceValue);
            if(discount > -1) item.setDiscount(discount);
            if(map.get("isActive") != null) item.setIsActive(Boolean.valueOf(map.get("isActive")));
            item.setLastChangeDate(new Timestamp(System.currentTimeMillis()));
        }
    }

    @Override
    public ItemPresenter findItemPresenterById(long id) {
        DefaultItem item = itemDao.findById(id);
        if(item == null){
            System.err.println("Item == null");
            throw new IllegalArgumentException();
        }
        item.getItemGroup().setChild(null);
        ItemPresenter presenter = item2Presenter(item);

        if(item instanceof ClothesItem){
            ClothesItemPresenter cPresenter = new ClothesItemPresenter(presenter);
            cPresenter.setBrand(((ClothesItem) item).getBrand());
            Collections.sort(((ClothesItem) item).getSize());
            cPresenter.setSize(((ClothesItem) item).getSize());
            cPresenter.setBrandImgUrl(settings.getBrandImgUrl());
            return cPresenter;
        }
        return presenter;
    }

    private UserGenPresenter user2Presenter(IUser u){
        if(u == null) return null;
        UserGenPresenter userGenPresenter = new UserGenPresenter();
        userGenPresenter.setId(u.getId());
        userGenPresenter.setFirstName(u.getFirstName());
        userGenPresenter.setEmail(u.getEmail());
        userGenPresenter.setLastName(u.getLastName());
        userGenPresenter.setCreateDt(u.getCreateDt());
        userGenPresenter.setPhotoUrl(u.getPhotoUrl());
        userGenPresenter.setState(u.getState());
        return userGenPresenter;
    }

    private ItemPresenter item2Presenter(DefaultItem item){
        String photoDir = settings.getPhotoDir()+"item_"+item.getId();
        String url = settings.getPhotoUrl() +"item_"+item.getId()+"/";

        ItemPresenter presenter = new ItemPresenter();

        presenter.setAddedBy(user2Presenter(item.getAddedBy()));
        presenter.setId(item.getId());
        presenter.setDiscount(item.getDiscount());
        presenter.setItemGroup(item.getItemGroup());
        presenter.setSalePrice(item.getSalePrice());
        presenter.setActive(item.isActive());
        presenter.setOriginPrice(item.getOriginPrice());
        presenter.setColor(item.getColor());
        presenter.setItemName(item.getItemName());
        presenter.setQuantity(item.getQuantity());
        presenter.setImages(urlImages(photoDir, "xl_.jpg", url));
        presenter.setThumbs(urlImages(photoDir, "s.jpg", url));
        presenter.setDescription(item.getDescription());
        presenter.setExtraNotes(item.getExtraNotes());
        presenter.setGender(item.getGender());
        return presenter;
    }

    private List<ItemImage> urlImages(String directory, String end, String url) {
        List<ItemImage> textFiles = new ArrayList<>();
        for (File file : new File(directory).listFiles()) {
            if (file.getName().endsWith(end)) {
                ItemImage image = new ItemImage();
                image.setSize(file.length());
                image.setName(file.getName());
                image.setUrl(url+file.getName());
                textFiles.add(image);
            }
        }
        return textFiles;
    }
}