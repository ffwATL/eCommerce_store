/*
package com.ffwatl;


import com.ffwatl.dao.items.ColorRepository;
import com.ffwatl.manage.entities.group.ItemGroup;
import com.ffwatl.manage.entities.items.brand.Brand;
import com.ffwatl.manage.entities.items.clothes.size.EuroSize;
import com.ffwatl.manage.entities.items.color.Color;
import com.ffwatl.manage.entities.users.Role;
import com.ffwatl.manage.entities.users.User;
import com.ffwatl.manage.entities.users.UserProfile;
import com.ffwatl.service.clothes.BrandService;
import com.ffwatl.service.group.ItemGroupService;
import com.ffwatl.service.items.EuroSizeService;
import com.ffwatl.service.users.UserProfileService;
import com.ffwatl.service.users.UserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Rollback(value = false)
@ContextConfiguration({"/spring/application-config.xml",  "/spring/spring-security.xml"})
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
public class AppFirstInit {

    private static final Logger logger = LogManager.getLogger();

    private static final Type EURO_SIZE_TYPE = new TypeToken<List<EuroSize>>() {
    }.getType();

    private static final Type BRAND_TYPE = new TypeToken<List<Brand>>() {
    }.getType();

    @Autowired
    private UserProfileService profileService;

    @Autowired
    private UserService userService;

    @Autowired
    private EuroSizeService euroSizeService;

    @Autowired
    private ItemGroupService itemGroupService;

    @Autowired
    private BrandService brandService;
    @Autowired
    private ColorRepository colorRepository;

    @Test
    @Ignore
    public void justTest() throws FileNotFoundException, UnsupportedEncodingException {
        Reader reader = new InputStreamReader(new FileInputStream("item_group_tree.json"),"UTF-8");
        ItemGroup i = new Gson().fromJson(reader, ItemGroup.class);
        System.err.println(i);
    }


    @Test
    public void A_setUserProfile(){
        List<UserProfile> list = new ArrayList<>();
        list.add(new UserProfile().setRole(Role.ADMINISTRATOR));
        list.add(new UserProfile().setRole(Role.MODERATOR));
        list.add(new UserProfile().setRole(Role.MANAGER));
        list.add(new UserProfile().setRole(Role.CUSTOMER));
        profileService.save(list);
    }

    @Test
    public void userCreationTest(){
        User user = new User();
        user.setFirstName("Misha");
        user.setEmail("4else@i.ua");
        user.setPassword("arsenalforever1");
        user.setUserName("ffw_ATL");
        Set<UserProfile> userProfiles = new HashSet<>();
        UserProfile u1 = profileService.findByRole(Role.ADMINISTRATOR);
        System.err.println("*********UserProfile: " + u1);
        userProfiles.add(u1);
        user.setUserProfiles(userProfiles);
        userService.save(user);
    }

    @Test
    public void fillBrand() throws FileNotFoundException, UnsupportedEncodingException {
        Reader reader = new InputStreamReader(new FileInputStream("brand.json"),"UTF-8");
        List<Brand> list = new Gson().fromJson(reader, BRAND_TYPE);
        brandService.save(list);
    }

    @Test
    public void itemGroupInit() throws FileNotFoundException, UnsupportedEncodingException {
        Reader reader = new InputStreamReader(new FileInputStream("item_group_tree.json"),"UTF-8");
        ItemGroup i = new Gson().fromJson(reader, ItemGroup.class);
        itemGroupService.save(i);

    }

    @Test
    public void colorInitTest() throws FileNotFoundException, UnsupportedEncodingException {
        Type colorType = new TypeToken<List<Color>>() {
        }.getType();
        Reader reader = new InputStreamReader(new FileInputStream("color.json"),"UTF-8");
        List<Color> list = new Gson().fromJson(reader, colorType);
        colorRepository.save(list);
    }

    @Test
    public void euroSizeInit() throws FileNotFoundException, UnsupportedEncodingException {
        Reader reader = new InputStreamReader(new FileInputStream("euro_size.json"),"UTF-8");
        List<EuroSize> e = new Gson().fromJson(reader, EURO_SIZE_TYPE);
        euroSizeService.save(e);
    }

*/
/*private void fillParent(ItemGroup parent, ItemGroup child){
            if(child == null) return;
            for(ItemGroup i: child.setParent(parent).getChild()){
                fillParent(child, i);
            }
        }*//*




}
*/
