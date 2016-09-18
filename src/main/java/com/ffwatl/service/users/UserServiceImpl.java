package com.ffwatl.service.users;


import com.ffwatl.dao.users.UserRepository;
import com.ffwatl.manage.entities.filter.grid_filter.GridFilter;
import com.ffwatl.manage.entities.users.User;
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
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}