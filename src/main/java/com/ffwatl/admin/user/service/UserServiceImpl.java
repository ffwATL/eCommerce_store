package com.ffwatl.admin.user.service;


import com.ffwatl.admin.catalog.domain.filter.grid_filter.GridFilter;
import com.ffwatl.common.service.ConverterDTO;
import com.ffwatl.admin.user.dao.UserDao;
import com.ffwatl.admin.user.dao.UserRepository;
import com.ffwatl.admin.user.domain.Address;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.admin.user.domain.UserImpl;
import com.ffwatl.admin.user.domain.UserProfile;
import com.ffwatl.admin.user.domain.dto.AddressDTO;
import com.ffwatl.admin.user.domain.dto.UserDTO;
import com.ffwatl.admin.user.domain.dto.UserProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ConverterDTO<User> implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void save(UserImpl u) {
        if(u == null) throw new IllegalArgumentException("UserImpl can't be null.");
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        userDao.save(u);
    }

    @Override
    public void removeById(long id) {
        userRepository.delete(id);
    }

    @Override
    public User findById(long id) {
        UserImpl u_original = userRepository.findOne(id);
        if(u_original == null) throw new IllegalArgumentException("No such user in DB");
        return u_original;
        /*return transformEntity2DTO(u_original);*/
    }

    @Override
    public Page<UserImpl> findAll(GridFilter f) {
        return null;
    }

    @Override
    public UserImpl findByUserName(String name) {
        return userRepository.findByUserName(name);
    }

    @Override
    public User findUserByEmail(String email){
        UserImpl u = findByEmail(email);
        if(u == null) throw new IllegalArgumentException("UserImpl with that email not found ;(");
        return transformEntity2DTO(u);
    }

    @Override
    public UserImpl findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserImpl transformDTO2Entity(User old) {
        return null;
    }

    @Override
    public User transformEntity2DTO(User old) {
        Address addressImpl = old.getAddress();
        Address addressDto = addressImpl == null ? null : new AddressDTO()
                .setCity(addressImpl.getCity())
                .setCountry(addressImpl.getCountry())
                .setId(addressImpl.getId())
                .setStreet(addressImpl.getStreet())
                .setZipCode(addressImpl.getZipCode());
        Set<UserProfile> userProfileSet = old.getUserProfiles().stream().map(profile -> new UserProfileDTO()
                .setId(profile.getId())
                .setRole(profile.getRole())).collect(Collectors.toSet());
        return new UserDTO()
                .setId(old.getId())
                .setAddress(addressDto)
                .setCreatedBy(old.getCreatedBy()!=null ? new UserDTO().setEmail(old.getCreatedBy().getEmail()): null)
                .setEmail(old.getEmail())
                .setFirstName(old.getFirstName())
                .setLastName(old.getLastName())
                .setOperatorCode(old.getOperatorCode())
                .setPhoneNumber(old.getPhoneNumber())
                .setPhotoUrl(old.getPhotoUrl())
                .setSkype(old.getSkype())
                .setState(old.getState())
                .setUserName(old.getUserName())
                .setUserProfiles(userProfileSet);
    }
}