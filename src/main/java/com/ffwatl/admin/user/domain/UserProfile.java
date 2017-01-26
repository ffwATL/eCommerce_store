package com.ffwatl.admin.user.domain;



public interface UserProfile {

    long getId();

    Role getRole();

    UserProfile setId(long id);

    UserProfile setRole(Role role);
}