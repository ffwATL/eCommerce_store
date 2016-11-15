package com.ffwatl.admin.user.domain;


import java.util.Date;
import java.util.Set;

public interface User {

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
    OperatorCode getOperatorCode();
    String getSkype();
    Date getCreateDt();
    User getCreatedBy();
    String getPhoneNumber();

    User setId(long id);
    User setUserName(String userName);
    User setEmail(String email);
    User setFirstName(String firstName);
    User setLastName(String lastName);
    User setOperatorCode(OperatorCode phone);
    User setSkype(String skype);
    User setCreateDt(Date createDt);
    User setPassword(String password);
    User setCreatedBy(User createdBy);
    User setAddress(Address address);
    User setPhotoUrl(String photoUrl);
    User setUserProfiles(Set<UserProfile> userProfiles);
    User setState(State state);
    User setPhoneNumber(String phoneNumber);
}