package com.ffwatl.dao.users;


import com.ffwatl.manage.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository  extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByUserName(String userName);

    User findByEmail(String email);

}