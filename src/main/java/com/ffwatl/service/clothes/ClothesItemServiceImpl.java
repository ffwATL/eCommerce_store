package com.ffwatl.service.clothes;

import com.ffwatl.dao.clothes.ClothesItemDao;
import com.ffwatl.manage.entities.items.clothes.ClothesItem;
import com.ffwatl.manage.entities.items.clothes.size.Size;
import com.ffwatl.manage.presenters.items.ClothesItemPresenter;
import com.ffwatl.service.group.ItemGroupService;
import com.ffwatl.service.items.ColorService;
import com.ffwatl.service.items.EuroSizeService;
import com.ffwatl.service.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClothesItemServiceImpl implements ClothesItemService{

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
    public Optional<List<ClothesItemPresenter>> findAll() {
        List<ClothesItemPresenter> list = new ArrayList<>();
        List<ClothesItem> clothesItemList = clothesItemDao.findAll();
        list.addAll(clothesItemList.stream().map(ClothesItemPresenter::new).collect(Collectors.toList()));
        return Optional.of(list);
    }

    @Override
    @Transactional
    public void removeById(long id) {
        clothesItemDao.remove(findById(id));
    }

    @Override
    @Transactional
    public void save(Optional<ClothesItemPresenter> optional, String email){
        if(!optional.isPresent()) throw new IllegalArgumentException("Items data is empty :(");
        clothesItemDao.save(presenter2Item(optional.get(), email));
    }

    private ClothesItem presenter2Item(ClothesItemPresenter presenter, String email){
        ClothesItem item;
        if(!presenter.isEdit()) {
            item = new ClothesItem();
            item.setImportDate(new Date());
            item.setAddedBy(userService.findByEmail(email));
        }
        else{
            item = findById(presenter.getId());
            item.setSize(presenter.getSize());
        }
        for(Size s: presenter.getSize()){
            s.setEu_size(euroSizeService.findById(s.getEu_size().getId()));
        }
        item.setSize(presenter.getSize());
        item.setItemGroup(itemGroupService.findById(presenter.getItemGroup().getId()));
        item.setColor(colorService.findById(presenter.getColor().getId()));
        item.setBrand(brandService.findById(presenter.getBrand().getId()));
        item.setQuantity(presenter.getQuantity());
        item.setId(presenter.getId());
        item.setIsActive(presenter.isActive());
        item.setGender(presenter.getGender());
        item.setDiscount(presenter.getDiscount());
        item.setExtraNotes(presenter.getExtraNotes());
        item.setDescription(presenter.getDescription());
        item.setItemName(presenter.getItemName());
        item.setSalePrice(presenter.getSalePrice());
        item.setOriginPrice(presenter.getOriginPrice());
        item.setCurrency(presenter.getCurrency());
        item.setLastChangeDate(new Timestamp(System.currentTimeMillis()));
        return item;
    }
}