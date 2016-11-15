package com.ffwatl.admin.user.dao;


import com.ffwatl.admin.user.domain.UserImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository  extends JpaRepository<UserImpl, Long>, JpaSpecificationExecutor<UserImpl> {

    UserImpl findByUserName(String userName);

    UserImpl findByEmail(String email);

}