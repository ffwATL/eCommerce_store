package com.ffwatl.admin.user.dao;


import com.ffwatl.admin.user.domain.Role;
import com.ffwatl.admin.user.domain.UserProfileImpl;

import java.util.List;

public interface UserProfileDao {

    UserProfileImpl findById(long id);
    UserProfileImpl findByRole(Role role);
    void save(UserProfileImpl userProfile);
    void remove(UserProfileImpl userProfile);
    List<UserProfileImpl> findAll();
}