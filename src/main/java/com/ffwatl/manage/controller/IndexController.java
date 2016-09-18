package com.ffwatl.manage.controller;

import com.ffwatl.service.clothes.ClothesItemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private Logger logger = LogManager.getLogger("com.ffwatl.manage.controller.IndexController");

    @Autowired
    private ClothesItemService clothesItemService;

    @RequestMapping(value = "/")
    public String adminRedirect(ModelMap modelMap){
        modelMap.addAttribute("user", getPrincipal());
        if(getPrincipal().equals("anonymousUser")) return "login/login";
        return "index";
    }

    @RequestMapping("/manage")
    public String helloWorld(ModelMap model){
        model.addAttribute("user", getPrincipal());
        return "index";
    }

    private String getPrincipal(){
        String userName;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}