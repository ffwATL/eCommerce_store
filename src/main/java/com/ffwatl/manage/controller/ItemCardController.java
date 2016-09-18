package com.ffwatl.manage.controller;

import com.ffwatl.service.items.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ItemCardController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/manage/overview/item")
    public String showItemCardAdmin(ModelMap model, @RequestParam long id){
        model.addAttribute("itemId", id);
        return "/manage/overview/itemCard";
    }
}