package com.ffwatl.manage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffwatl.manage.entities.items.brand.Brand;
import com.ffwatl.manage.entities.items.clothes.ClothesItem;
import com.ffwatl.service.clothes.BrandService;
import com.ffwatl.service.clothes.ClothesItemService;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Controller
public class AddNewItemController {

    @Autowired
    private BrandService brandService;
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
        ClothesItem clothesItem;
        try {
            clothesItem = new ObjectMapper().readValue(item, ClothesItem.class);
            if(clothesItem == null) {
                logger.error("NullPointer");
                throw new NullPointerException();
            }
            clothesItemService.save(clothesItem, SecurityContextHolder.getContext().getAuthentication().getName());
            proceedImages(settings.getPhotoDir() + "item_" + clothesItem.getId(), file);
            logger.info("clothes item saved: " + clothesItem);
        } catch (Exception e) {
            logger.error(e.getMessage());
            model.addAttribute("isError", true);
            model.addAttribute("errorMessage", e.getMessage());
            return "manage/new/result";
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

    private void proceedImages(String dirPath, List<MultipartFile> file) throws IOException {
        WebUtil.createFolder(dirPath);
        int count = 1;
        for(MultipartFile f: file){
            resizeAndSave(f, dirPath, count++);
        }
    }

    private void resizeAndSave(MultipartFile f, String dirPath, int count) throws IOException {
        if (f == null) return;
        try(OutputStream os = new FileOutputStream(new File(dirPath + "\\"+"image"+count+"xl.jpg"))) {
            ImageIO.write(Scalr.resize(ImageIO.read(f.getInputStream()),
                    115), "jpeg", new File(dirPath + "\\image" + count + "s.jpg"));
            ImageIO.write(Scalr.resize(ImageIO.read(f.getInputStream()),
                    230), "jpeg", new File(dirPath + "\\image"+count+"m.jpg"));
            ImageIO.write(Scalr.resize(ImageIO.read(f.getInputStream()),
                    370), "jpeg", new File(dirPath + "\\image"+count+"l.jpg"));
            os.write(f.getBytes());
        }catch (IOException e){
            logger.error(e.getMessage());
            throw e;
        }
    }

}