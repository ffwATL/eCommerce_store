package com.ffwatl.admin.user.service;


import com.ffwatl.common.persistence.FetchMode;
import com.ffwatl.common.service.ConverterDTO;
import com.ffwatl.admin.user.dao.UserProfileDao;
import com.ffwatl.admin.user.domain.Role;
import com.ffwatl.admin.user.domain.UserProfile;
import com.ffwatl.admin.user.domain.UserProfileImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserProfileServiceImpl extends ConverterDTO<UserProfile> implements UserProfileService{

    @Autowired
    private UserProfileDao userProfileDao;

    @Override
    public UserProfile findById(long id) {
        return userProfileDao.findById(id);
    }

    @Override
    @Transactional
    public void save(UserProfile userProfile) {
        userProfileDao.save(transformDTO2Entity(userProfile, FetchMode.LAZY));
    }

    @Override
    @Transactional
    public void save(List<UserProfile> list) {
        List<UserProfile> listEntity = transformList(list, ENTITY_OBJECT, FetchMode.LAZY);
        for(UserProfile u: listEntity){
            userProfileDao.save((UserProfileImpl) u);
        }
    }

    @Override
    @Transactional
    public void remove(UserProfile userProfile) {
        userProfileDao.remove(transformDTO2Entity(userProfile, FetchMode.LAZY));
    }

    @Override
    public List<UserProfile> findAll() {
        return transformList(userProfileDao.findAll(), DTO_OBJECT, FetchMode.LAZY);
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
        return userProfileDao.findByRole(role);
    }

    @Override
    public UserProfile transformEntity2DTO(UserProfile old, FetchMode fetchMode) {
        return null;
    }

    @Override
    public UserProfileImpl transformDTO2Entity(UserProfile old, FetchMode fetchMode){
        return (UserProfileImpl) new UserProfileImpl().setId(old.getId()).setRole(old.getRole());
    }
}