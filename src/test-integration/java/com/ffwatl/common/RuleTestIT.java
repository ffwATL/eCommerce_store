package com.ffwatl.common;


import com.ffwatl.admin.offer.service.discount.PromotableOrderItem;
import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.common.rule.Rule;
import com.ffwatl.common.rule.ValueType;
import com.ffwatl.common.rule.dao.RuleDao;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Field;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@ContextConfiguration({"/spring/spring-application-context.xml"})
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })

public class RuleTestIT {

    @Resource(name = "rule_dao")
    private RuleDao ruleDao;

    @Resource(name = "promotableOrderItem4Rule")
    private PromotableOrderItem promotableOrderItem;

    @Test
    @DatabaseSetup("/dataset/rule.xml")
    public void testIntToDoubleBoundValueCheck() throws Exception {
        Rule rule = ruleDao.findById(1);
        assertThat(rule, notNullValue());

        System.err.println(rule);
        System.err.println(promotableOrderItem);

        try {
            OrderItem orderItem = promotableOrderItem.getOrderItem();
            Class<?> clazz = orderItem.getClass();
            Field field = clazz.getDeclaredField(rule.getFieldName());
            field.setAccessible(true);

            ValueType valueType = rule.getFieldType();
            switch (valueType) {
            case NUMBER: {
                double value = field.getDouble(orderItem);
                double ruleValue = Double.valueOf(rule.getBoundValue());
                System.err.println("***********************");
                System.err.println("OrderItem value: " + value + ", ruleValue: " + ruleValue);
                System.err.println("value >= ruleValue: " + (value >= ruleValue));
                System.err.println("***********************");
            }
        }


        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
