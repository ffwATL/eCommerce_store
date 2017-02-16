package com.ffwatl.common.rule.dao;


import com.ffwatl.common.rule.Rule;
import com.ffwatl.common.rule.RuleImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository("rule_dao")
public class RuleDaoImpl implements RuleDao{

    @PersistenceContext
    private EntityManager em;


    @Override
    public Rule findById(long id) {
        return em.find(RuleImpl.class, id);
    }

    @Override
    public List<Rule> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Rule> criteria = cb.createQuery(Rule.class);
        Root<RuleImpl> root = criteria.from(RuleImpl.class);

        criteria.select(root);
        return em.createQuery(criteria).getResultList();
    }

    @Override
    public void removeById(long id) {
        Rule rule = findById(id);
        em.remove(rule);
    }

    @Override
    public void remove(Rule rule) {
        if(!em.contains(rule)){
            rule = findById(rule.getId());
        }
        em.remove(rule);
    }

    @Override
    public Rule save(Rule rule) {
        return em.merge(rule);
    }
}