package com.ffwatl.service.clothes;

import com.ffwatl.dao.clothes.ClothesItemDao;
import com.ffwatl.manage.entities.items.clothes.ClothesItem;
import com.ffwatl.manage.entities.items.clothes.size.Size;
import com.ffwatl.service.group.ItemGroupService;
import com.ffwatl.service.items.ColorService;
import com.ffwatl.service.items.EuroSizeService;
import com.ffwatl.service.users.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class ClothesItemServiceImpl implements ClothesItemService{

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ClothesItemDao clothesItemDao;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private EuroSizeService euroSizeService;
    @Autowired
    private ItemGroupService itemGroupService;
    @Autowired
    private UserService userService;


    @Override
    public ClothesItem findById(long id) {
        return clothesItemDao.findById(id);
    }

    @Override
    @Transactional
    public void removeById(long id) {
        clothesItemDao.remove(findById(id));
    }

    @Override
    @Transactional
    public void save(ClothesItem item, String email) throws IOException {
        item.setAddedBy(userService.findByEmail(email));
        item.setColor(colorService.findById(item.getColor().getId()));
        item.setItemGroup(itemGroupService.findById(item.getItemGroup().getId()));
        item.setBrand(brandService.findById(item.getBrand().getId()));
        for (Size s: item.getSize()){
            s.setEu_size(euroSizeService.findById(s.getEu_size().getId()));
        }
        item.setImportDate(new Date());
        item.setLastChangeDate(new Timestamp(System.currentTimeMillis()));
        clothesItemDao.save(item);
    }


}