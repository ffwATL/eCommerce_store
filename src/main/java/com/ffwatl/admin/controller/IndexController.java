package com.ffwatl.admin.controller;

import com.ffwatl.service.users.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes("loggedInUser")
public class IndexController {

    private Logger logger = LogManager.getLogger();

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/")
    public String adminRedirect(ModelMap modelMap, HttpServletRequest request,
                                @CookieValue(value = "app", defaultValue = "none") String lang){
        modelMap.addAttribute("user", getPrincipal());
        if(getPrincipal().equals("anonymousUser")) return "login/login";
        modelMap.addAttribute("loggedInUser", userService.findUserByEmail(getPrincipal()));
        if(lang.equals("none")) request.setAttribute("lang", request.getLocale().getLanguage());
        return "index";
    }

    @RequestMapping("/admin")
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