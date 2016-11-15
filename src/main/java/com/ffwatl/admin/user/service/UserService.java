package com.ffwatl.admin.user.service;


import com.ffwatl.admin.catalog.domain.filter.grid_filter.GridFilter;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.admin.user.domain.UserImpl;
import org.springframework.data.domain.Page;

public interface UserService{

    void save(UserImpl u);

    void removeById(long id);

    User findById(long id);

    Page<UserImpl> findAll(GridFilter f);

    UserImpl findByUserName(String name);

    User findUserByEmail(String email);

    UserImpl findByEmail(String email);

}