package com.ffwatl.admin.catalog.domain.filter.specification;


import com.ffwatl.admin.catalog.domain.*;
import com.ffwatl.admin.catalog.domain.filter.grid_filter.GridFilterRule;
import com.ffwatl.admin.catalog.domain.BrandImpl;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


public class ProductClothesSpecification extends ProductDefaultSpecification<ProductClothes> {


    @SuppressWarnings("unchecked")
    public Specification<ProductClothes> isSizeEquals(List<GridFilterRule> rules){
        return new Specification<ProductClothes>() {
            @Override
            public Predicate toPredicate(Root<ProductClothes> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);
                Join<ProductClothes, ProductAttribute> join_size = root.join("size", JoinType.LEFT);
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

    public Specification<ProductClothes> isColorEquals(List<GridFilterRule> rules){
        return new Specification<ProductClothes>() {
            @Override
            public Predicate toPredicate(Root<ProductClothes> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);
                Join<ProductClothes, Color> colorJoin = root.join("color", JoinType.INNER);
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
    public Specification<ProductClothes> isBrandEquals(List<GridFilterRule> rules){
        return new Specification<ProductClothes>() {
            @Override
            public Predicate toPredicate(Root<ProductClothes> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);
                Join<ProductClothes, BrandImpl> brandJoin = root.join("brand", JoinType.INNER);
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

    public Specification<ProductClothes> isGenderEquals(List<GridFilterRule> rules){
        return new Specification<ProductClothes>() {
            @Override
            public Predicate toPredicate(Root<ProductClothes> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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