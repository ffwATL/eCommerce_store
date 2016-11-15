package com.ffwatl.admin.user.domain.dto;


import com.ffwatl.admin.user.domain.Role;
import com.ffwatl.admin.user.domain.UserProfile;

public class UserProfileDTO implements UserProfile{

    private long id;
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
    public String toString() {
        return "UserProfileDTO{" +
                "id=" + id +
                ", role=" + role +
                '}';
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
        UserProfile other = (UserProfile) obj;
        if (id != other.getId())
            return false;
        if (role == null) {
            if (other.getRole() != null)
                return false;
        } else if (!role.equals(other.getRole()))
            return false;
        return true;
    }

}