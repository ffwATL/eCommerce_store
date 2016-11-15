package com.ffwatl.admin.user.service;


import com.ffwatl.admin.user.domain.Role;
import com.ffwatl.admin.user.domain.UserProfile;

import java.util.List;

public interface UserProfileService {

    UserProfile findById(long id);

    void save(UserProfile userProfile);

    void save(List<UserProfile>list);

    void remove(UserProfile userProfile);

    List<UserProfile> findAll();

    List<UserProfile> findById(long ... id);

    UserProfile findByRole(Role role);
}