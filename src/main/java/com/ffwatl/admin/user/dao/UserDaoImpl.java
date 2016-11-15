package com.ffwatl.admin.user.dao;


import com.ffwatl.admin.user.domain.UserImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserDaoImpl implements UserDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(UserImpl user) {
        em.persist(user);
    }

    @Override
    public UserImpl findById(long id){
        return em.find(UserImpl.class, id);
    }

    @Override
    public UserImpl findByUserName(String name) {
        return em.createQuery("SELECT u FROM UserImpl u WHERE u.userName=:name", UserImpl.class)
                .setParameter("name", name).getSingleResult();
    }

    @Override
    public UserImpl findByEmail(String email) {
        return em.createQuery("SELECT u FROM UserImpl u WHERE u.email=:email", UserImpl.class)
                .setParameter("email", email).getSingleResult();
    }

    @Override
    public void removeById(long id) {
        em.remove(findById(id));
    }
}