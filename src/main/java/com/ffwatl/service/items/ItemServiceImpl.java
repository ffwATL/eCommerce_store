package com.ffwatl.service.items;


import com.ffwatl.dao.items.ItemDao;
import com.ffwatl.domain.items.Item;
import com.ffwatl.domain.items.clothes.ClothesItem;
import com.ffwatl.domain.items.clothes.size.Size;
import com.ffwatl.domain.update.ItemUpdate;
import com.ffwatl.service.clothes.BrandService;
import com.ffwatl.service.group.ItemGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
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

    @Override
    public Item findById(long id) {
        Item item = itemDao.findById(id);
        item.getItemGroup().setChild(null);
        return item;
    }

    @Override
    public List<Item> findAll() {
        return itemDao.findAll();
    }

    @Override
    @Transactional
    public void save(Item item) {
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
    public void remove(Item item) {
        itemDao.remove(item);
    }

    @Override
    @Transactional
    public void updateSingleItem(ItemUpdate update){
        Map<String, String> map = update.getOptions();
        long[] ids = update.getIdentifiers();
        for (long id : ids) {
            Item item = findById(id);
            if (map.get("isActive") != null) item.setIsActive(Boolean.valueOf(map.get("isActive")));
            if (map.get("itemName") != null) item.setItemName(map.get("itemName"));
            if (map.get("salePrice") != null) item.setSalePrice(Integer.valueOf(map.get("salePrice")));
            if (map.get("discount") != null) item.setDiscount(Integer.valueOf(map.get("discount")));
            if (map.get("color") != null) item.setColor(colorService.findById(Long.valueOf(map.get("color"))));
            if (map.get("itemGroup") != null)
                item.setItemGroup(itemGroupService.findById(Long.valueOf(map.get("itemGroup"))));
            item.setLastChangeDate(new Timestamp(System.currentTimeMillis()));
        }
    }

    @Override
    @Transactional
    public void updateItems(ItemUpdate update) {
        Map<String, String> map = update.getOptions();
        long[] ids = update.getIdentifiers();
        int priceValue = map.get("priceValue") != null ? Integer.valueOf(map.get("priceValue")) : 0;
        int discount = map.get("discount") != null ? Integer.valueOf(map.get("discount")) : -1;
        for (long id: ids){
            Item item = findById(id);
            item.setSalePrice(item.getSalePrice() + priceValue);
            if(discount > -1) item.setDiscount(discount);
            item.setLastChangeDate(new Timestamp(System.currentTimeMillis()));
        }
    }
}