package com.ffwatl.admin.user.service;

import com.ffwatl.admin.user.domain.UserImpl;
import com.ffwatl.admin.user.domain.UserProfile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger("com.ffwatl.profile.customUserDetailsService");

    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        logger.info("** inside loadUserByUsername");
        UserImpl user = userService.findByEmail(name);
        logger.info("UserImpl: " + user);
        if(user == null){
            logger.error("UserImpl not found");
            System.out.println("UserImpl not found");
            throw new UsernameNotFoundException("Username not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                /*user.getState().name().equals("Active")*/ true, true, true, true, getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(UserImpl user){
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(UserProfile userProfile : user.getUserProfiles()){
            System.out.println("UserProfileImpl : "+userProfile);
            authorities.add(new SimpleGrantedAuthority("ROLE_"+userProfile.getRole()));
        }
        System.out.print("authorities :"+authorities);
        return authorities;
    }
}