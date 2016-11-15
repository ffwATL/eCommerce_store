package com.ffwatl.admin.user.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Class describes user profile
 */
@Entity
@Table(name = "users")
public class UserImpl implements User {

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

    @OneToOne(cascade = CascadeType.ALL, targetEntity = OperatorCodeImpl.class)
    private OperatorCode operatorCode;
    private String phoneNumber;
    @OneToOne(cascade = CascadeType.ALL, targetEntity = AddressImpl.class)
    private Address address;
    private String skype;
    private Date createDt;

    private State state = State.ACTIVE;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, targetEntity = UserProfileImpl.class)
    @JoinTable(name = "USER_U_PROFILE",
            joinColumns = { @JoinColumn(name = "USER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "USER_PROFILE_ID") })
    private Set<UserProfile> userProfiles = new HashSet<>();

    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = UserImpl.class)
    private User createdBy;

    public long getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
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

    public OperatorCode getOperatorCode() {
        return operatorCode;
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

    public User setId(long id) {
        this.id = id;
        return this;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public User setOperatorCode(OperatorCode code) {
        this.operatorCode = code;
        return this;
    }

    public User setSkype(String skype) {
        this.skype = skype;
        return this;
    }

    public User setCreateDt(Date createDt) {
        this.createDt = createDt;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public User setAddress(Address address) {
        this.address = address;
        return this;
    }

    public User setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    public User setUserProfiles(Set<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
        return this;
    }

    public User setState(State state) {
        this.state = state;
        return this;
    }

    @Override
    public String toString() {
        return "UserImpl{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", operatorCode=" + operatorCode +
                ", address=" + address +
                ", skype='" + skype + '\'' +
                ", createDt=" + createDt +
                ", state=" + state +
                ", userProfiles=" + userProfiles +
                ", createdBy=" + createdBy +
                '}';
    }
}