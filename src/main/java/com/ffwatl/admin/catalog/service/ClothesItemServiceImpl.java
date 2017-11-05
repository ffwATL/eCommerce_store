package com.ffwatl.admin.catalog.service;

import com.ffwatl.admin.catalog.dao.ProductDao;
import com.ffwatl.admin.catalog.domain.*;
import com.ffwatl.admin.catalog.domain.dto.FieldDTO;
import com.ffwatl.admin.catalog.domain.dto.ProductAttributeDTO;
import com.ffwatl.admin.user.service.UserService;
import com.ffwatl.common.service.ConverterDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClothesItemServiceImpl implements ClothesItemService{

    private static final Logger logger = LogManager.getLogger("com.ffwatl.admin.web.controller.AddNewItemController");
    private static final SizeDTOConverter sizeDTOConverter = new SizeDTOConverter();

    @Autowired
    private ProductDao clothesProductDao;

    @Autowired
    private BrandService brandService;

    @Autowired
    private EuroSizeService euroSizeService;

    @Autowired
    private ItemGroupService itemGroupService;

    @Autowired
    private UserService userService;


    @Override
    public ProductImpl findById(long id) {
        return clothesProductDao.findById(id);
    }

    @Override
    public Optional<List<ProductImpl>> findAll() {
        /*List<ClothesItemPresenter> list = new ArrayList<>();
        List<ProductClothes> clothesItemList = clothesProductDao.findAll();
        list.addAll(clothesItemList.stream().map(ClothesItemPresenter::new).collect(Collectors.toList()));
        return Optional.of(list);*/
        return Optional.empty();
    }

    @Override
    @Transactional
    public void removeById(long id) {
        clothesProductDao.remove(findById(id));
    }

    @Override
    @Transactional
    public long save(Optional<ClothesItemPresenter> optional, String email){
        if(!optional.isPresent()) throw new IllegalArgumentException("Items data is empty :(");
        ProductImpl item = presenter2Item(optional.get(), email);
        clothesProductDao.save(item);
        return item.getId();
    }

    @Override
    @Transactional
    public void save(Optional<List<ClothesItemPresenter>> optionals){
        /*for(ClothesItemPresenter item: optionals.get()){
            clothesProductDao.save(presenter2Item(item, item.getAddedBy().getEmail()));
        }*/
    }

    private ProductImpl presenter2Item(ClothesItemPresenter presenter, String email){
        ProductImpl item;
        /*if(!presenter.isEdit()) {
            item = new ProductClothes();
            item.setImportDate(new Date());
            item.setAddedBy(userService.findByEmail(email));
        }
        else{
            item = findById(presenter.getId());
            item.setSize(sizeDTOConverter.transformList(presenter.getSize(), ConverterDTO.ENTITY_OBJECT));
        }
        for(ProductAttribute s: presenter.getSize()){
            s.setProductAttributeType(euroSizeService.findById(s.getProductAttributeType().getId()));
        }

        item.setSize(sizeDTOConverter.transformList(presenter.getSize(), ConverterDTO.ENTITY_OBJECT));
        item.setProductCategory(itemGroupService.findById(presenter.getProductCategory().getId()));
        item.setColorName(colorService.findById(presenter.getColorName().getId()));
        item.setBrand(brandService.findById(presenter.getBrand().getId()));
        item.setQuantity(presenter.getQuantity());
        item.setId(presenter.getId());
        item.setActive(presenter.isActive());
        item.setGender(presenter.getGender());
        item.setDiscount(presenter.getDiscount());
        item.setExtraNotes(presenter.getExtraNotes());
        item.setDescription(presenter.getDescription());
        item.setProductName(presenter.getProductName());
        item.setSalePrice(presenter.getSalePrice());
        item.setRetailPrice(presenter.getRetailPrice());
        item.setCurrency(presenter.getCurrency());
        item.setLastChangeDate(new Timestamp(System.currentTimeMillis()));*/

        return null;
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
                    .setProductAttributeType(old.getProductAttributeType())
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
                    .setProductAttributeType(old.getProductAttributeType())
                    .setQuantity(old.getQuantity())
                    .setFields(fields);
        }
    }

}