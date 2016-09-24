package com.ffwatl.manage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffwatl.manage.entities.items.brand.Brand;
import com.ffwatl.manage.entities.items.clothes.ClothesItem;
import com.ffwatl.manage.entities.users.User;
import com.ffwatl.service.clothes.BrandService;
import com.ffwatl.service.clothes.ClothesItemService;
import com.ffwatl.service.group.ItemGroupService;
import com.ffwatl.service.users.UserService;
import com.ffwatl.util.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class AddNewItemController {


    @Autowired
    private ItemGroupService itemGroupService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ClothesItemService clothesItemService;
    @Autowired
    private UserService userService;

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
    public String addClothesItem(@RequestParam("files[]") List<MultipartFile> file,
                                                 ModelMap model, @RequestParam String item) throws IOException {
        ClothesItem clothesItem;
        file.remove(file.size() - 1);
        try {
            clothesItem = new ObjectMapper().readValue(item, ClothesItem.class);
            if(clothesItem == null) {
                logger.error("NullPointer");
                throw new NullPointerException();
            }
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findByEmail(auth.getName());
            clothesItem.setAddedBy(user);
            clothesItemService.save(clothesItem);
            logger.info("clothes item saved: " + clothesItem);
            String dirPath = settings.getPhotoDir() + "item_" + clothesItem.getId();
            Path path = Paths.get(dirPath).toAbsolutePath();
            Files.createDirectory(path);
            int count = 1;
            for(MultipartFile f: file){
                saveImages(f, dirPath, count++);
                logger.info("image saved: " + f.getOriginalFilename() + ", size: " + f.getBytes().length);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            model.addAttribute("isError", true);
            model.addAttribute("errorMessage", e.getMessage());
            /*throw e;*/
            return "manage/new/result";
        }
        model.addAttribute("itemId",clothesItem.getId());
        model.addAttribute("isError", false);
        model.addAttribute("itemName", clothesItem.getItemName());
        return "manage/new/result";
    }



    private void saveImages(MultipartFile f, String dirPath, int count) throws IOException{
        if (f == null) return;
        File file = new File(dirPath + "\\"+"image"+count+"xl.jpg");
        try(OutputStream os = new FileOutputStream(file)) {
            ImageIO.write(Scalr.resize(ImageIO.read(f.getInputStream()),
                    115), "jpeg", new File(dirPath + "\\image"+count+"s.jpg"));
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