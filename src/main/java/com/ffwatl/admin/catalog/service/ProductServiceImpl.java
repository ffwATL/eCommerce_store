package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.dao.ProductDao;
import com.ffwatl.admin.catalog.domain.Product;
import com.ffwatl.admin.catalog.domain.ProductAttribute;
import com.ffwatl.admin.catalog.domain.ProductImpl;
import com.ffwatl.admin.catalog.domain.presenter.ItemUpdatePresenter;
import com.ffwatl.admin.catalog.domain.presenter.ProductImage;
import com.ffwatl.admin.catalog.domain.presenter.ProductUpdateImpl;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.admin.user.domain.dto.UserDTO;
import com.ffwatl.common.persistence.FetchMode;
import com.ffwatl.util.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("product_service")
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LogManager.getLogger("com.ffwatl.admin.web.controller.AddNewItemController");

    @Autowired
    private ProductDao productDao;

    @Autowired
    private BrandService brandService;

    @Autowired
    private EuroSizeService euroSizeService;

    @Autowired
    private ItemGroupService itemGroupService;

    @Autowired
    private Settings settings;


    @Override
    public ProductImpl findById(long id) {
        ProductImpl item = productDao.findById(id);
        item.getProductCategory().setChild(null);
        return item;
    }

    @Override
    public Product findById(long id, FetchMode fetchMode) {
        if(id < 1) {
            throw new IllegalArgumentException("Wrong Product ID given: " + id);
        }
        return productDao.findById(id, fetchMode);
    }

    @Override
    public List<ProductImpl> findAll() {
        return productDao.findAll();
    }

    @Override
    public List<Product> findAll(FetchMode fetchMode) {
        return productDao.findAll(fetchMode);
    }

    @Override
    @Transactional
    public void save(ProductImpl item) {
        item.setCategory(itemGroupService.findById(item.getProductCategory().getId()));

        item.setBrand(brandService.findById(item.getBrand().getId()));
        for (ProductAttribute s: item.getProductAttributes()){
           /* s.setProductAttributeType(euroSizeService.findById(s.getProductAttributeType().getId()));*/ //FIXME: update pls!!
        }

        productDao.save(item);
    }

    @Override
    public Product save(Product product) {
        return productDao.save(product);
    }

    @Override
    public void remove(Product item) {
        if(item == null) {
            throw new IllegalArgumentException("Null Product is given");
        }
        productDao.remove(item);
    }

    @Override
    @Transactional
    public void changeItemStatus(ProductImpl item){
        ProductImpl item_1 = findById(item.getId());
        item_1.setActive(item.isActive());
    }

    @Override
    @Transactional
    public void updateSingleItem(ItemUpdatePresenter update){
        Product freshItem = update.getItem();
        ProductImpl item = findById(freshItem.getId());
        if(item == null) {
            throw new IllegalArgumentException("Probably wrong Product id. Product not found :( [id]="+freshItem.getId() );
        }
        item.setCategory(itemGroupService.findById(freshItem.getProductCategory().getId()));
        item.setProductName(freshItem.getProductName());
        item.setActive(freshItem.isActive());
        item.setSalePrice(freshItem.getSalePrice());

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
            ProductImpl item = findById(id);
            item.setSalePrice(item.getSalePrice() + priceValue);

            if(map.get("isActive") != null) item.setActive(Boolean.valueOf(map.get("isActive")));
            item.setLastChangeDate(new Timestamp(System.currentTimeMillis()));
        }
    }

    @Override
    public ProductUpdateImpl findItemPresenterById(long id) {
        /*ProductImpl item = productDao.findById(id);
        if(item == null){
            System.err.println("Product == null");
            throw new IllegalArgumentException();
        }
        item.getProductCategory().setChild(null);
        ProductUpdateImpl presenter = item2Presenter(item);

        if(item instanceof ProductImpl){
            ClothesItemPresenter cPresenter = new ClothesItemPresenter(presenter);
            cPresenter.setBrand(((ProductClothes) item).getBrand());
            Collections.sort(((ProductClothes) item).getSize());
            cPresenter.setSize(((ProductClothes) item).getSize());
            cPresenter.setBrandImgUrl(settings.getBrandImgUrl());
            LOGGER.info("*****" + ((ProductClothes) item).getSize());
            return cPresenter;
        }
        LOGGER.info("not clothes");
        return presenter;*/
        return null;
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

    private ProductUpdateImpl item2Presenter(ProductImpl item){
        String photoDir = settings.getPhotoDir()+"item_"+item.getId();
        String url = settings.getPhotoUrl() +"item_"+item.getId()+"/";

        ProductUpdateImpl presenter = new ProductUpdateImpl();

        presenter.setAddedBy(user2Presenter(item.getAddedBy()));
        presenter.setId(item.getId());

        presenter.setCategory(item.getProductCategory());
        presenter.setSalePrice(item.getSalePrice());
        presenter.setActive(item.isActive());
        presenter.setRetailPrice(item.getRetailPrice());
        presenter.setProductName(item.getProductName());
        presenter.setQuantity(item.getQuantity());
        presenter.setImages(urlImages(photoDir, "xl_.jpg", url));
        presenter.setThumbs(urlImages(photoDir, "s.jpg", url));
        presenter.setDescription(item.getDescription());
        presenter.setExtraNotes(item.getExtraNotes());
        return presenter;
    }

    private List<ProductImage> urlImages(String directory, String end, String url) {
        List<ProductImage> textFiles = new ArrayList<>();
        File[] files = new File(directory).listFiles();
        if(files != null){
            for (File file : files) {
                if (file.getName().endsWith(end)) {
                    ProductImage image = new ProductImage();
                    image.setSize(file.length());
                    image.setName(file.getName());
                    image.setUrl(url+file.getName());
                    textFiles.add(image);
                }
            }
        }

        return textFiles;
    }
}