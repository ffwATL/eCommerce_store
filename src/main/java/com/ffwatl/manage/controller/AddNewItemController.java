package com.ffwatl.manage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffwatl.manage.entities.items.brand.Brand;
import com.ffwatl.manage.entities.items.clothes.ClothesItem;
import com.ffwatl.service.clothes.BrandService;
import com.ffwatl.service.clothes.ClothesItemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class AddNewItemController {

    @Autowired
    private BrandService brandService;
    @Autowired
    private ClothesItemService clothesItemService;


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
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            clothesItem = new ObjectMapper().readValue(item, ClothesItem.class);
            if(clothesItem == null) {
                logger.error("NullPointer");
                throw new NullPointerException();
            }
            file.remove(file.size() - 1);
            clothesItemService.save(clothesItem, file, email);
            logger.info("clothes item saved: " + clothesItem);
        } catch (Exception e) {
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

}