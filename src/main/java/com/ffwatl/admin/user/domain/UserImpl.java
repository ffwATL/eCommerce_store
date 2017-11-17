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

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name="psw_hash", nullable = false)
    private String password;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "photo_url")
    private String photoUrl;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = OperatorCodeImpl.class)
    @JoinColumn(name = "operator_code_id")
    private OperatorCode operatorCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = AddressImpl.class)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "skype")
    private String skype;

    @Column(name = "create_dt")
    private Date createDt;

    @Column(name = "state")
    private State state = State.ACTIVE;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, targetEntity = UserProfileImpl.class)
    @JoinTable(name = "USER_U_PROFILE",
            joinColumns = { @JoinColumn(name = "USER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "USER_PROFILE_ID") })
    @JoinColumn(name = "user_profile_id")
    private Set<UserProfile> userProfiles = new HashSet<>();

    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = UserImpl.class)
    @JoinColumn(name = "user_id")
    private User createdBy;


    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public Set<UserProfile> getUserProfiles() {
        return userProfiles;
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public String getPhotoUrl() {
        return photoUrl;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
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

    @Override
    public Date getCreateDt() {
        return createDt;
    }

    @Override
    public User getCreatedBy() {
        return createdBy;
    }


    @Override
    public User setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    @Override
    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Override
    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public User setOperatorCode(OperatorCode code) {
        this.operatorCode = code;
        return this;
    }

    @Override
    public User setSkype(String skype) {
        this.skype = skype;
        return this;
    }

    @Override
    public User setCreateDt(Date createDt) {
        this.createDt = createDt;
        return this;
    }

    @Override
    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public User setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    @Override
    public User setAddress(Address address) {
        this.address = address;
        return this;
    }

    @Override
    public User setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    @Override
    public User setUserProfiles(Set<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
        return this;
    }

    @Override
    public User setState(State state) {
        this.state = state;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserImpl user = (UserImpl) o;

        if (getUserName() != null ? !getUserName().equals(user.getUserName()) : user.getUserName() != null)
            return false;

        return getEmail() != null ? getEmail().equals(user.getEmail()) : user.getEmail() == null;
    }

    @Override
    public int hashCode() {
        int result = (getUserName() != null ? getUserName().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserImpl{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", createDt=" + createDt +
                ", state=" + state +
                ", userProfiles=" + userProfiles +
                ", createdBy=" + createdBy +
                '}';
    }
}