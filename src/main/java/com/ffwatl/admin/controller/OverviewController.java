package com.ffwatl.admin.controller;


import com.ffwatl.admin.entities.group.ItemGroup;
import com.ffwatl.service.group.ItemGroupService;
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

    private static final Logger logger = LogManager.getLogger("com.ffwatl.admin.controller.OverviewController");

    @Autowired
    private ItemGroupService itemGroupService;


    @RequestMapping(value = "/admin/overview/all", method = RequestMethod.GET)
    public String getAllItems(HttpServletRequest request, ModelMap model, @RequestParam(required = false) String lang){
        Cookie[] cookies = request.getCookies();
        String cookie = LocaleContextHolder.getLocale().getDisplayLanguage();
        if(cookies != null){
            for(Cookie c: cookies){
                if(c.getName().equals("app")) cookie = c.getValue();
            }
        }
        List<ItemGroup> result = itemGroupService.findByLvlLazyWithoutChild(1);
        resolveI18n(lang != null ? lang : cookie, result);
        model.addAttribute("globalCategories", result);
        return "manage/overview/itemOverview";
    }

    private void resolveI18n(String lang, List<ItemGroup> list){
        switch (lang) {
            case "en":
                for (ItemGroup i : list) {
                    String l = i.getGroupName().getLocale_en();
                    i.getGroupName().setLocale_ru(l);
                    i.getGroupName().setLocale_ua(l);
                }
            case "ru":
                for (ItemGroup i : list) {
                    String l = i.getGroupName().getLocale_ru();
                    i.getGroupName().setLocale_en(l);
                    i.getGroupName().setLocale_ua(l);
                }
            case "ua":
                for (ItemGroup i : list) {
                    String l = i.getGroupName().getLocale_ua();
                    i.getGroupName().setLocale_en(l);
                    i.getGroupName().setLocale_ru(l);
                }
        }

    }
}