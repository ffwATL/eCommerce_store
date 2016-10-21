package com.ffwatl.manage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffwatl.manage.entities.items.brand.Brand;
import com.ffwatl.manage.presenters.items.ClothesItemPresenter;
import com.ffwatl.service.clothes.BrandService;
import com.ffwatl.service.clothes.ClothesItemService;
import com.ffwatl.service.items.SizeService;
import com.ffwatl.util.Settings;
import com.ffwatl.util.WebUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Controller
public class AddNewItemController {

    @Autowired
    private BrandService brandService;
    @Autowired
    private SizeService sizeService;
    @Autowired
    private ClothesItemService clothesItemService;
    @Autowired
    private Settings settings;


    private static final Logger logger = LogManager.getLogger("com.ffwatl.manage.controller.AddNewItemController");

    @RequestMapping(value = "/manage/new/item", method = RequestMethod.GET)
    public String addNewItem(HttpServletRequest request, ModelMap model,
                             @RequestParam(required = false) String lang){
        Cookie[] cookies = request.getCookies();
        String cookie = LocaleContextHolder.getLocale().getDisplayLanguage();
        if(cookies != null){
            for(Cookie c: cookies){
                if(c.getName().equals("app")) cookie = c.getValue();
            }
        }
        List<Brand> brandList = brandService.findAll();
        model.addAttribute("brandList", brandList);
        model.addAttribute("lang", lang != null ? lang : cookie);
        return "manage/new/newItem";
    }

    @RequestMapping(value = "/manage/new/item/clothes", method = RequestMethod.POST)
    public String addClothesItem(@RequestParam("files[]") List<MultipartFile> file, HttpServletRequest request,
                                 ModelMap model, @RequestParam String item) throws IOException {
        ClothesItemPresenter clothesItem;
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        String dir;
        try {
            clothesItem = new ObjectMapper().readValue(item, ClothesItemPresenter.class);
            clothesItemService.save(Optional.of(clothesItem), email);
            dir = settings.getPhotoDir() + "item_" + clothesItem.getId();
            if(clothesItem.isEdit()) {
                for(int id: clothesItem.getRemovedImgs()){
                    deleteImagesByEnds(dir,id+"s.jpg");
                    deleteImagesByEnds(dir,id+"m.jpg");
                    deleteImagesByEnds(dir,id+"l.jpg");
                    deleteImagesByEnds(dir,id+"xl.jpg");
                }
            }
            proceedImages(dir, file, clothesItem.isEdit());
            if(clothesItem.isEdit()) {
                sizeService.removeById(clothesItem.getOldSizes());
                logger.info("Item with id = " + clothesItem.getId() + " was UPDATED by user: " + email);
            }
            else logger.info("New item was ADDED by user: " + email + ", item name: " +
                    clothesItem.getItemName().getLocale_en()+", id = "+clothesItem.getId());
        } catch (Exception e) {
            /*logger.error(e.getMessage());*/
            throw e;
            /*model.addAttribute("isError", true);
            model.addAttribute("errorMessage", e.getMessage());
            return "manage/new/result";*/
        }
        Cookie[] cookies = request.getCookies();
        String cookie = LocaleContextHolder.getLocale().getDisplayLanguage();
        if(cookies != null){
            for(Cookie c: cookies){
                if(c.getName().equals("app")) cookie = c.getValue();
            }
        }
        model.addAttribute("itemId", clothesItem.getId());
        model.addAttribute("isError", false);
        model.addAttribute("itemName", clothesItem.getItemName().getValue(cookie));
        return "manage/new/result";
    }

    private void proceedImages(String dirPath, List<MultipartFile> file, boolean editMode) throws IOException {
        if(!editMode) WebUtil.createFolder(dirPath);
        int count = finder(dirPath, "xl.jpg").length + 1;
        for(MultipartFile f: file){
            resizeAndSave(f, dirPath, count++);
        }
    }

    private void resizeAndSave(MultipartFile f, String dirPath, int count) throws IOException {
        if (f == null || f.getInputStream() == null) return;
        BufferedImage image = ImageIO.read(f.getInputStream());
        if(image == null) return;
        try(OutputStream os = new FileOutputStream(new File(dirPath + "\\"+"image"+count+"xl.jpg"))) {
            ImageIO.write(Scalr.resize(image,115), "jpeg", new File(dirPath + "\\image"+count+"s.jpg"));
            ImageIO.write(Scalr.resize(image,230), "jpeg", new File(dirPath + "\\image"+count+"m.jpg"));
            ImageIO.write(Scalr.resize(image,370), "jpeg", new File(dirPath + "\\image"+count+"l.jpg"));
            os.write(f.getBytes());
        }catch (IOException e){
            logger.error("Error while saving item images");
            logger.error(e.getMessage());
            throw e;
        }
    }

    private void deleteImagesByEnds(String dirPath, String ends){
        for(File f: finder(dirPath, ends)){
            String name = f.getName();
            try {
                Files.deleteIfExists(f.toPath());
                logger.trace(name + " DELETED");
            } catch (IOException e) {
                logger.error("Error on delete file: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private File[] finder(String dirName, String endName){
        return new File(dirName).listFiles((dir1, filename) -> {return filename.endsWith(endName);});
    }
}