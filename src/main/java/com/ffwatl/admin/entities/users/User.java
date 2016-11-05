package com.ffwatl.admin.entities.users;

import com.ffwatl.admin.entities.users.address.Address;
import com.ffwatl.admin.entities.users.phone.Phone;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Class describes user profile
 */
@Entity
@Table(name = "users")
public class User implements IUser{

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

    private String photoUrl;

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

    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = User.class)
    private IUser createdBy;

    public long getId() {
        return id;
    }

    public Set<UserProfile> getUserProfiles() {
        return userProfiles;
    }

    public State getState() {
        return state;
    }

    public String getPhotoUrl() {
        return photoUrl;
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

    public IUser getCreatedBy() {
        return createdBy;
    }

    public IUser setId(long id) {
        this.id = id;
        return this;
    }

    public IUser setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public IUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public IUser setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public IUser setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public IUser setPhone(Phone phone) {
        this.phone = phone;
        return this;
    }

    public IUser setSkype(String skype) {
        this.skype = skype;
        return this;
    }

    public IUser setCreateDt(Date createDt) {
        this.createDt = createDt;
        return this;
    }

    public IUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public IUser setCreatedBy(IUser createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public IUser setAddress(Address address) {
        this.address = address;
        return this;
    }

    public IUser setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    public IUser setUserProfiles(Set<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
        return this;
    }

    public IUser setState(State state) {
        this.state = state;
        return this;
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
                ", photoUrl='" + photoUrl + '\'' +
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