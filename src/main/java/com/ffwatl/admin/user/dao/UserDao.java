package com.ffwatl.admin.user.dao;


import com.ffwatl.admin.user.domain.UserImpl;

public interface UserDao {

    void save(UserImpl user);

    UserImpl findByUserName(String name);

    UserImpl findByEmail(String email);

    void removeById(long id);

    UserImpl findById(long id);
}