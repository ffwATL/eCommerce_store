package com.ffwatl.admin.entities.users;


import com.ffwatl.admin.entities.users.address.Address;
import com.ffwatl.admin.entities.users.phone.Phone;

import java.util.Date;
import java.util.Set;

public interface IUser {

    long getId();
    Set<UserProfile> getUserProfiles();
    State getState();
    String getPhotoUrl();
    String getPassword();
    Address getAddress();
    String getUserName();
    String getEmail();
    String getFirstName();
    String getLastName();
    Phone getPhone();
    String getSkype();
    Date getCreateDt();
    IUser getCreatedBy();

    IUser setId(long id);
    IUser setUserName(String userName);
    IUser setEmail(String email);
    IUser setFirstName(String firstName);
    IUser setLastName(String lastName);
    IUser setPhone(Phone phone);
    IUser setSkype(String skype);
    IUser setCreateDt(Date createDt);
    IUser setPassword(String password);
    IUser setCreatedBy(IUser createdBy);
    IUser setAddress(Address address);
    IUser setPhotoUrl(String photoUrl);
    IUser setUserProfiles(Set<UserProfile> userProfiles);
    IUser setState(State state);
}