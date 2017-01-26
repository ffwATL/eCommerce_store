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

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "email", nullable = false)
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserImpl user = (UserImpl) o;

        if (getId() != user.getId()) return false;
        if (getUserName() != null ? !getUserName().equals(user.getUserName()) : user.getUserName() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(user.getEmail()) : user.getEmail() != null) return false;
        if (getPassword() != null ? !getPassword().equals(user.getPassword()) : user.getPassword() != null)
            return false;
        if (getFirstName() != null ? !getFirstName().equals(user.getFirstName()) : user.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(user.getLastName()) : user.getLastName() != null)
            return false;
        if (getPhotoUrl() != null ? !getPhotoUrl().equals(user.getPhotoUrl()) : user.getPhotoUrl() != null)
            return false;
        if (getOperatorCode() != null ? !getOperatorCode().equals(user.getOperatorCode()) : user.getOperatorCode() != null)
            return false;
        if (getPhoneNumber() != null ? !getPhoneNumber().equals(user.getPhoneNumber()) : user.getPhoneNumber() != null)
            return false;
        if (getAddress() != null ? !getAddress().equals(user.getAddress()) : user.getAddress() != null) return false;
        if (getSkype() != null ? !getSkype().equals(user.getSkype()) : user.getSkype() != null) return false;
        if (getCreateDt() != null ? !getCreateDt().equals(user.getCreateDt()) : user.getCreateDt() != null)
            return false;
        if (getState() != user.getState()) return false;
        if (getUserProfiles() != null ? !getUserProfiles().equals(user.getUserProfiles()) : user.getUserProfiles() != null)
            return false;
        return !(getCreatedBy() != null ? !getCreatedBy().equals(user.getCreatedBy()) : user.getCreatedBy() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getUserName() != null ? getUserName().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getPhotoUrl() != null ? getPhotoUrl().hashCode() : 0);
        result = 31 * result + (getOperatorCode() != null ? getOperatorCode().hashCode() : 0);
        result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getSkype() != null ? getSkype().hashCode() : 0);
        result = 31 * result + (getCreateDt() != null ? getCreateDt().hashCode() : 0);
        result = 31 * result + (getState() != null ? getState().hashCode() : 0);
        result = 31 * result + (getUserProfiles() != null ? getUserProfiles().hashCode() : 0);
        result = 31 * result + (getCreatedBy() != null ? getCreatedBy().hashCode() : 0);
        return result;
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
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address=" + address +
                ", skype='" + skype + '\'' +
                ", createDt=" + createDt +
                ", state=" + state +
                ", userProfiles=" + userProfiles +
                ", createdBy=" + createdBy +
                '}';
    }
}