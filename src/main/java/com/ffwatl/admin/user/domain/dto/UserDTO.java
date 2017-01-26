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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDTO userDTO = (UserDTO) o;

        if (getId() != userDTO.getId()) return false;
        if (getEmail() != null ? !getEmail().equals(userDTO.getEmail()) : userDTO.getEmail() != null) return false;
        if (getUserName() != null ? !getUserName().equals(userDTO.getUserName()) : userDTO.getUserName() != null)
            return false;
        if (getFirstName() != null ? !getFirstName().equals(userDTO.getFirstName()) : userDTO.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(userDTO.getLastName()) : userDTO.getLastName() != null)
            return false;
        if (getPhotoUrl() != null ? !getPhotoUrl().equals(userDTO.getPhotoUrl()) : userDTO.getPhotoUrl() != null)
            return false;
        if (getState() != userDTO.getState()) return false;
        if (getUserProfiles() != null ? !getUserProfiles().equals(userDTO.getUserProfiles()) : userDTO.getUserProfiles() != null)
            return false;
        if (getOperatorCode() != null ? !getOperatorCode().equals(userDTO.getOperatorCode()) : userDTO.getOperatorCode() != null)
            return false;
        if (getAddress() != null ? !getAddress().equals(userDTO.getAddress()) : userDTO.getAddress() != null)
            return false;
        if (getSkype() != null ? !getSkype().equals(userDTO.getSkype()) : userDTO.getSkype() != null) return false;
        if (getPhoneNumber() != null ? !getPhoneNumber().equals(userDTO.getPhoneNumber()) : userDTO.getPhoneNumber() != null)
            return false;
        if (getCreateDt() != null ? !getCreateDt().equals(userDTO.getCreateDt()) : userDTO.getCreateDt() != null)
            return false;
        return !(getCreatedBy() != null ? !getCreatedBy().equals(userDTO.getCreatedBy()) : userDTO.getCreatedBy() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getUserName() != null ? getUserName().hashCode() : 0);
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getPhotoUrl() != null ? getPhotoUrl().hashCode() : 0);
        result = 31 * result + (getState() != null ? getState().hashCode() : 0);
        result = 31 * result + (getUserProfiles() != null ? getUserProfiles().hashCode() : 0);
        result = 31 * result + (getOperatorCode() != null ? getOperatorCode().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getSkype() != null ? getSkype().hashCode() : 0);
        result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
        result = 31 * result + (getCreateDt() != null ? getCreateDt().hashCode() : 0);
        result = 31 * result + (getCreatedBy() != null ? getCreatedBy().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", state=" + state +
                ", userProfiles=" + userProfiles +
                ", operatorCode=" + operatorCode +
                ", address=" + address +
                ", skype='" + skype + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", createDt=" + createDt +
                ", createdBy=" + createdBy +
                '}';
    }
}