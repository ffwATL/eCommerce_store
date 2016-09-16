package com.ffwatl.dao.users;


import com.ffwatl.domain.users.Role;
import com.ffwatl.domain.users.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    UserProfile findByRole(Role role);
}