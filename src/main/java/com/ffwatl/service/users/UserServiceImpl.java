package com.ffwatl.service.users;


import com.ffwatl.dao.users.UserRepository;
import com.ffwatl.admin.filter.grid_filter.GridFilter;
import com.ffwatl.admin.entities.users.User;
import com.ffwatl.admin.presenters.users.UserGenPresenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void save(User u) {
        if(u == null) throw new IllegalArgumentException("User can't be null.");
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        userRepository.save(u);
    }

    @Override
    public void removeById(long id) {
        userRepository.delete(id);
    }

    @Override
    public User findById(long id) {
        return userRepository.findOne(id);
    }

    @Override
    public Page<User> findAll(GridFilter f) {
        return null;
    }

    @Override
    public User findByUserName(String name) {
        return userRepository.findByUserName(name);
    }

    @Override
    public UserGenPresenter findUserByEmail(String email){
        User u = findByEmail(email);
        if(u == null) throw new IllegalArgumentException("User with that email not found ;(");
        UserGenPresenter presenter = new UserGenPresenter();
        presenter.setEmail(u.getEmail());
        presenter.setState(u.getState());
        presenter.setPhotoUrl(u.getPhotoUrl());
        presenter.setFirstName(u.getFirstName());
        presenter.setLastName(u.getLastName());
        presenter.setId(u.getId());
        presenter.setCreateDt(u.getCreateDt());
        return presenter;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}