package com.ffwatl.admin.user.domain;


import javax.persistence.*;

@Entity
@Table(name="user_profile")
public class UserProfileImpl implements UserProfile {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name="role", length=2, unique=true, nullable=false)
    private Role role = Role.CUSTOMER;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Role getRole() {
        return role;
    }

    @Override
    public UserProfile setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public UserProfile setRole(Role role) {
        this.role = role;
        return this;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) id;
        result = prime * result + ((role == null) ? 0 : role.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (!(obj instanceof UserProfile))
            return false;
        UserProfileImpl other = (UserProfileImpl) obj;
        if (id != other.id)
            return false;
        if (role == null) {
            if (other.role != null)
                return false;
        } else if (!role.equals(other.role))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UserProfileImpl [id=" + id + ",  type=" + role + "]";
    }

}