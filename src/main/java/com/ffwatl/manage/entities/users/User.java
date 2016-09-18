package com.ffwatl.manage.entities.users;

import com.ffwatl.manage.entities.users.address.Address;
import com.ffwatl.manage.entities.users.phone.Phone;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Class describes user profile
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;
    private String lastName;

    private String avatar;

    @OneToOne(cascade = CascadeType.ALL)
    private Phone phone;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    private String skype;
    private Date createDt;

    private State state = State.ACTIVE;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "USER_U_PROFILE",
            joinColumns = { @JoinColumn(name = "USER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "USER_PROFILE_ID") })
    private Set<UserProfile> userProfiles = new HashSet<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User createdBy;

    public long getId() {
        return id;
    }

    public Set<UserProfile> getUserProfiles() {
        return userProfiles;
    }

    public State getState() {
        return state;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getPassword() {
        return password;
    }

    public Address getAddress() {
        return address;
    }

    public String getUserName() {
        return userName;
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

    public Phone getPhone() {
        return phone;
    }

    public String getSkype() {
        return skype;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setUserProfiles(Set<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", phone=" + phone +
                ", address=" + address +
                ", skype='" + skype + '\'' +
                ", createDt=" + createDt +
                ", state=" + state +
                ", userProfiles=" + userProfiles +
                ", createdBy=" + createdBy +
                '}';
    }
}