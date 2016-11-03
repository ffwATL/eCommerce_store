package com.ffwatl.dao.users;


import com.ffwatl.admin.entities.users.Role;
import com.ffwatl.admin.entities.users.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    UserProfile findByRole(Role role);
}