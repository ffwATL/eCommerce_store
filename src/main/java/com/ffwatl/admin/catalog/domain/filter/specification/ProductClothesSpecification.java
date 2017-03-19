package com.ffwatl.admin.catalog.domain.filter.specification;


import com.ffwatl.admin.catalog.domain.*;
import com.ffwatl.admin.catalog.domain.filter.grid_filter.GridFilterRule;
import com.ffwatl.admin.catalog.domain.BrandImpl;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


public class ProductClothesSpecification extends ProductDefaultSpecification<ProductImpl> {


    @SuppressWarnings("unchecked")
    public Specification<ProductImpl> isSizeEquals(List<GridFilterRule> rules){
        return new Specification<ProductImpl>() {
            @Override
            public Predicate toPredicate(Root<ProductImpl> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);
                Join<ProductImpl, ProductAttribute> join_size = root.join("size", JoinType.LEFT);
                Join<ProductAttribute, ProductAttributeType> join_euSize = join_size.join("eu_size", JoinType.INNER);
                List<Predicate> predicates = new ArrayList<>();
                if(rules != null){
                    for (GridFilterRule r: rules){
                        predicates.add(cb.equal(join_euSize.get(r.getField()), Long.valueOf(r.getData())));
                    }
                }
                return cb.or(predicates.toArray(new Predicate[predicates.size()]));
            }
        ;
    };}

    public Specification<ProductImpl> isColorEquals(List<GridFilterRule> rules){
        return new Specification<ProductImpl>() {
            @Override
            public Predicate toPredicate(Root<ProductImpl> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);
                Join<ProductImpl, Color> colorJoin = root.join("color", JoinType.INNER);
                List<Predicate> predicates = new ArrayList<>();
                if(rules != null){
                    for (GridFilterRule r: rules){
                        predicates.add(cb.equal(colorJoin.get(r.getField()), Long.valueOf(r.getData())));
                    }
                }
                return cb.or(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
    public Specification<ProductImpl> isBrandEquals(List<GridFilterRule> rules){
        return new Specification<ProductImpl>() {
            @Override
            public Predicate toPredicate(Root<ProductImpl> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);
                Join<ProductImpl, BrandImpl> brandJoin = root.join("brand", JoinType.INNER);
                List<Predicate> predicates = new ArrayList<>();
                if(rules != null){
                    for(GridFilterRule r: rules){
                        predicates.add(cb.equal(brandJoin.get(r.getField()), Long.valueOf(r.getData())));
                    }
                }
                return cb.or(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

    public Specification<ProductImpl> isGenderEquals(List<GridFilterRule> rules){
        return new Specification<ProductImpl>() {
            @Override
            public Predicate toPredicate(Root<ProductImpl> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);
                List<Predicate> predicates = new ArrayList<>();
                if(rules != null){
                    for(GridFilterRule r: rules){
                        predicates.add(cb.equal(root.get(r.getField()), Integer.valueOf(r.getData())));
                    }
                }
                return cb.or(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}