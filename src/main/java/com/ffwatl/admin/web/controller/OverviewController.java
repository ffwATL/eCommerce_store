package com.ffwatl.admin.web.controller;


import com.ffwatl.admin.catalog.domain.ProductCategory;
import com.ffwatl.admin.catalog.service.ProductCategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@SessionAttributes("loggedInUser")
public class OverviewController {

    private static final Logger logger = LogManager.getLogger("com.ffwatl.admin.web.controller.OverviewController");

    @Autowired
    private ProductCategoryService productCategoryService;


    @RequestMapping(value = "/admin/overview/all", method = RequestMethod.GET)
    public String getAllItems(HttpServletRequest request, ModelMap model, @RequestParam(required = false) String lang){
        Cookie[] cookies = request.getCookies();
        String cookie = LocaleContextHolder.getLocale().getDisplayLanguage();
        if(cookies != null){
            for(Cookie c: cookies){
                if(c.getName().equals("app")) cookie = c.getValue();
            }
        }
        List<ProductCategory> result = productCategoryService.findByLevel(1);
        resolveI18n(lang != null ? lang : cookie, result);
        model.addAttribute("globalCategories", result);
        return "admin/overview/itemOverview";
    }

    private void resolveI18n(String lang, List<ProductCategory> list){
        switch (lang) {
            case "en":
                for (ProductCategory i : list) {
                    String l = i.getCategoryName().getLocale_en();
                    i.getCategoryName().setLocale_ru(l);
                    i.getCategoryName().setLocale_ua(l);
                }
            case "ru":
                for (ProductCategory i : list) {
                    String l = i.getCategoryName().getLocale_ru();
                    i.getCategoryName().setLocale_en(l);
                    i.getCategoryName().setLocale_ua(l);
                }
            case "ua":
                for (ProductCategory i : list) {
                    String l = i.getCategoryName().getLocale_ua();
                    i.getCategoryName().setLocale_en(l);
                    i.getCategoryName().setLocale_ru(l);
                }
        }

    }
}