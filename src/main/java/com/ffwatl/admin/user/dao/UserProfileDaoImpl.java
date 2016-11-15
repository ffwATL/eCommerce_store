package com.ffwatl.admin.user.dao;


import com.ffwatl.admin.user.domain.Role;
import com.ffwatl.admin.user.domain.UserProfileImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserProfileDaoImpl implements UserProfileDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public UserProfileImpl findById(long id) {
        return em.find(UserProfileImpl.class, id);
    }

    @Override
    public UserProfileImpl findByRole(Role role) {
        return em.createQuery("SELECT u FROM UserProfileImpl u WHERE u.role=:role", UserProfileImpl.class)
                .setParameter("role", role).getSingleResult();
    }

    @Override
    public void save(UserProfileImpl userProfile) {
        em.persist(userProfile);
    }

    @Override
    public void remove(UserProfileImpl userProfile) {
        em.remove(userProfile);
    }

    @Override
    public List<UserProfileImpl> findAll() {
        return em.createQuery("SELECT u FROM UserProfileImpl u", UserProfileImpl.class).getResultList();
    }
}