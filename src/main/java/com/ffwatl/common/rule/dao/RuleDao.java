package com.ffwatl.common.rule.dao;


import com.ffwatl.common.rule.Rule;

import java.util.List;

public interface RuleDao {

    Rule findById(long id);

    List<Rule> findAll();

    void removeById(long id);

    void remove(Rule rule);

    Rule save(Rule rule);
}