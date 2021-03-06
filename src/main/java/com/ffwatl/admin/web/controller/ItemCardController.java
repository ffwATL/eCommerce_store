package com.ffwatl.admin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("loggedInUser")
public class ItemCardController {

    @RequestMapping(value = "/admin/overview/item")
    public String showItemCardAdmin(ModelMap model, @RequestParam long id){
        model.addAttribute("itemId", id);
        return "/admin/overview/itemCard";
    }
}