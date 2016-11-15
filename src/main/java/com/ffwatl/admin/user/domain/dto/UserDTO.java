package com.ffwatl.admin.user.domain.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ffwatl.admin.user.domain.*;

import java.util.Date;
import java.util.Set;

public class UserDTO implements User {

    private long id;
    private String email;
    private String userName;
    private String firstName;
    private String lastName;
    private String photoUrl;
    private State state;

    @JsonDeserialize(contentAs=UserProfileDTO.class)
    private Set<UserProfile> userProfiles;
    @JsonDeserialize(as=OperatorCodeDTO.class)
    private OperatorCode operatorCode;
    @JsonDeserialize(as=AddressDTO.class)
    private Address address;
    private String skype;
    private String phoneNumber;
    private Date createDt;
    @JsonDeserialize(as=UserDTO.class)
    private User createdBy;

    public long getId() {
        return id;
    }

    @Override
    public Set<UserProfile> getUserProfiles() {
        return userProfiles;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public OperatorCode getOperatorCode() {
        return operatorCode;
    }

    @Override
    public String getSkype() {
        return skype;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    public Date getCreateDt() {
        return createDt;
    }

    @Override
    public User getCreatedBy() {
        return createdBy;
    }

    public State getState() {
        return state;
    }

    public User setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public User setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    public User setCreateDt(Date createDt) {
        this.createDt = createDt;
        return this;
    }

    @Override
    public User setPassword(String password) {
        throw new UnsupportedOperationException("Can't set password");
    }

    public User setState(State state) {
        this.state = state;
        return this;
    }

    @Override
    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    @Override
    public User setUserProfiles(Set<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
        return this;
    }

    @Override
    public User setOperatorCode(OperatorCode code) {
        this.operatorCode = code;
        return this;
    }

    @Override
    public User setAddress(Address address) {
        this.address = address;
        return this;
    }

    @Override
    public User setSkype(String skype) {
        this.skype = skype;
        return this;
    }

    @Override
    public User setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", createDt=" + createDt +
                ", state=" + state +
                '}';
    }
}