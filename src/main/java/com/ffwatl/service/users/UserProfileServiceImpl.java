package com.ffwatl.service.users;


import com.ffwatl.dao.users.UserProfileRepository;
import com.ffwatl.manage.entities.users.Role;
import com.ffwatl.manage.entities.users.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService{

    @Autowired
    private UserProfileRepository profileRepository;

    @Override
    public UserProfile findById(long id) {
        return profileRepository.findOne(id);
    }

    @Override
    @Transactional
    public void save(UserProfile userProfile) {
        profileRepository.save(userProfile);
    }

    @Override
    @Transactional
    public void save(List<UserProfile> list) {
        profileRepository.save(list);
    }

    @Override
    @Transactional
    public void remove(UserProfile userProfile) {
        profileRepository.delete(userProfile);
    }

    @Override
    public List<UserProfile> findAll() {
        return profileRepository.findAll();
    }

    @Override
    public List<UserProfile> findById(long... id) {
        List<UserProfile> list = new ArrayList<>(id.length);
        for (long anId : id) {
            UserProfile u = findById(anId);
            if (u != null) list.add(u);
        }
        return list;
    }

    @Override
    public UserProfile findByRole(Role role) {
        return profileRepository.findByRole(role);
    }
}