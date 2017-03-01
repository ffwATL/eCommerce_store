package com.ffwatl.admin.catalog.service;

import com.ffwatl.admin.catalog.dao.ClothesItemDao;
import com.ffwatl.admin.catalog.domain.*;
import com.ffwatl.admin.catalog.domain.dto.FieldDTO;
import com.ffwatl.admin.catalog.domain.dto.ProductAttributeDTO;
import com.ffwatl.admin.catalog.domain.presenter.ClothesItemPresenter;
import com.ffwatl.admin.user.service.UserService;
import com.ffwatl.common.service.ConverterDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger("com.ffwatl.admin.web.controller.AddNewItemController");
    private static final SizeDTOConverter sizeDTOConverter = new SizeDTOConverter();

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
    public ProductClothes findById(long id) {
        return clothesItemDao.findById(id);
    }

    @Override
    public Optional<List<ClothesItemPresenter>> findAll() {
        List<ClothesItemPresenter> list = new ArrayList<>();
        List<ProductClothes> clothesItemList = clothesItemDao.findAll();
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
    public long save(Optional<ClothesItemPresenter> optional, String email){
        if(!optional.isPresent()) throw new IllegalArgumentException("Items data is empty :(");
        ProductClothes item = presenter2Item(optional.get(), email);
        clothesItemDao.save(item);
        return item.getId();
    }

    @Override
    @Transactional
    public void save(Optional<List<ClothesItemPresenter>> optionals){
        for(ClothesItemPresenter item: optionals.get()){
            clothesItemDao.save(presenter2Item(item, item.getAddedBy().getEmail()));
        }
    }

    private ProductClothes presenter2Item(ClothesItemPresenter presenter, String email){
        ProductClothes item;
        if(!presenter.isEdit()) {
            item = new ProductClothes();
            item.setImportDate(new Date());
            item.setAddedBy(userService.findByEmail(email));
        }
        else{
            item = findById(presenter.getId());
            item.setSize(sizeDTOConverter.transformList(presenter.getSize(), ConverterDTO.ENTITY_OBJECT));
        }
        for(ProductAttribute s: presenter.getSize()){
            s.setEu_size(euroSizeService.findById(s.getEu_size().getId()));
        }

        item.setSize(sizeDTOConverter.transformList(presenter.getSize(), ConverterDTO.ENTITY_OBJECT));
        item.setItemGroup(itemGroupService.findById(presenter.getCategory().getId()));
        item.setColor(colorService.findById(presenter.getColor().getId()));
        item.setBrand(brandService.findById(presenter.getBrand().getId()));
        item.setQuantity(presenter.getQuantity());
        item.setId(presenter.getId());
        item.setActive(presenter.isActive());
        item.setGender(presenter.getGender());
        item.setDiscount(presenter.getDiscount());
        item.setExtraNotes(presenter.getExtraNotes());
        item.setDescription(presenter.getDescription());
        item.setItemName(presenter.getProductName());
        item.setSalePrice(presenter.getSalePrice());
        item.setRetailPrice(presenter.getRetailPrice());
        item.setCurrency(presenter.getCurrency());
        item.setLastChangeDate(new Timestamp(System.currentTimeMillis()));

        return item;
    }

    private static class SizeDTOConverter extends ConverterDTO<ProductAttribute>{

        @Override
        public ProductAttribute transformDTO2Entity(ProductAttribute old) {
            List<Field> fields = old.getFields().stream().map(f -> new FieldImpl()
                    .setId(f.getId())
                    .setName(f.getName())
                    .setValue(f.getValue()))
                    .collect(Collectors.toList());
            return new ProductAttributeImpl()
                    .setId(old.getId())
                    .setEu_size(old.getEu_size())
                    .setQuantity(old.getQuantity())
                    .setFields(fields);
        }

        @Override
        public ProductAttribute transformEntity2DTO(ProductAttribute old) {
            List<Field> fields = old.getFields().stream().map(f -> new FieldDTO()
                    .setId(f.getId())
                    .setName(f.getName())
                    .setValue(f.getValue()))
                    .collect(Collectors.toList());
            return new ProductAttributeDTO()
                    .setId(old.getId())
                    .setEu_size(old.getEu_size())
                    .setQuantity(old.getQuantity())
                    .setFields(fields);
        }
    }

}