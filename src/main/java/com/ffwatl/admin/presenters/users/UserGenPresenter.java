package com.ffwatl.admin.presenters.users;


import com.ffwatl.admin.entities.users.State;

import java.util.Date;

public class UserGenPresenter {

    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String photoUrl;
    private Date createDt;
    private State state;

    public long getId() {
        return id;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public State getState() {
        return state;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "UserGenPresenter{" +
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