package com.ffwatl.service.users;


import com.ffwatl.manage.filter.grid_filter.GridFilter;
import com.ffwatl.manage.entities.users.User;
import org.springframework.data.domain.Page;

public interface UserService{

    void save(User u);

    void removeById(long id);

    User findById(long id);

    Page<User> findAll(GridFilter f);

    User findByUserName(String name);

    User findByEmail(String email);

}