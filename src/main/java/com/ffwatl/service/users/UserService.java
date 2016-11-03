package com.ffwatl.service.users;


import com.ffwatl.admin.filter.grid_filter.GridFilter;
import com.ffwatl.admin.entities.users.User;
import com.ffwatl.admin.presenters.users.UserGenPresenter;
import org.springframework.data.domain.Page;

public interface UserService{

    void save(User u);

    void removeById(long id);

    User findById(long id);

    Page<User> findAll(GridFilter f);

    User findByUserName(String name);

    UserGenPresenter findUserByEmail(String email);

    User findByEmail(String email);

}