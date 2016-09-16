package com.ffwatl.service.users;


import com.ffwatl.domain.users.Role;
import com.ffwatl.domain.users.UserProfile;

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