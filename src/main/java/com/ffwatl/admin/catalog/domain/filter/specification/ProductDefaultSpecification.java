package com.ffwatl.admin.catalog.domain.filter.specification;


import com.ffwatl.admin.catalog.domain.ProductDefault;
import com.ffwatl.admin.catalog.domain.filter.grid_filter.GridFilterRule;
import com.ffwatl.admin.catalog.domain.CategoryImpl;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDefaultSpecification<T extends ProductDefault> {

    public Specification<T> isGroupEquals(List<GridFilterRule> rules){
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);
                Join<ProductDefault, CategoryImpl> join_itemGroup = root.join("itemGroup", JoinType.INNER);
                List<Predicate> predicates = new ArrayList<>();
                if(rules != null){
                    for (GridFilterRule r: rules){
                        predicates.add(cb.equal(join_itemGroup.get(r.getField()), Long.valueOf(r.getData())));
                    }
                }
                return cb.or(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

    public Specification<T> isInPriceRange(List<GridFilterRule> rules){
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);
                if(rules != null){
                    Expression<Double> name = root.get(rules.get(0).getField());
                    return  cb.between(name, Double.valueOf(rules.get(0).getData()), Double.valueOf(rules.get(1).getData()));
                }
                return cb.and(new ArrayList<Predicate>().toArray(new Predicate[0]));
            }
        };
    }

    public Specification<T> isActive(List<GridFilterRule> rules){
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);
                if(rules != null){
                    return  cb.equal(root.get(rules.get(0).getField()), Boolean.valueOf(rules.get(0).getData()));
                }
                return cb.and(new ArrayList<Predicate>().toArray(new Predicate[0]));
            }
        };
    }

    public Specification<T> isSale(List<GridFilterRule> rules){
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);
                if(rules != null){
                    return  cb.greaterThan(root.get(rules.get(0).getField()), 1);
                }
                return cb.and(new ArrayList<Predicate>().toArray(new Predicate[0]));
            }
        };
    }
}