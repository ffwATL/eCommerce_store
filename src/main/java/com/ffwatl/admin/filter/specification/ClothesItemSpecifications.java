package com.ffwatl.admin.filter.specification;


import com.ffwatl.admin.entities.items.clothes.size.EuroSize;
import com.ffwatl.admin.entities.items.color.Color;
import com.ffwatl.admin.filter.grid_filter.GridFilterRule;
import com.ffwatl.admin.entities.items.clothes.ClothesItem;
import com.ffwatl.admin.entities.items.brand.Brand;
import com.ffwatl.admin.entities.items.clothes.size.Size;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class ClothesItemSpecifications extends ItemSpecification<ClothesItem>{


    @SuppressWarnings("unchecked")
    public Specification<ClothesItem> isSizeEquals(List<GridFilterRule> rules){
        return new Specification<ClothesItem>() {
            @Override
            public Predicate toPredicate(Root<ClothesItem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);
                Join<ClothesItem, Size> join_size = root.join("size", JoinType.LEFT);
                Join<Size, EuroSize> join_euSize = join_size.join("eu_size", JoinType.INNER);
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

    public Specification<ClothesItem> isColorEquals(List<GridFilterRule> rules){
        return new Specification<ClothesItem>() {
            @Override
            public Predicate toPredicate(Root<ClothesItem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);
                Join<ClothesItem, Color> colorJoin = root.join("color", JoinType.INNER);
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
    public Specification<ClothesItem> isBrandEquals(List<GridFilterRule> rules){
        return new Specification<ClothesItem>() {
            @Override
            public Predicate toPredicate(Root<ClothesItem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);
                Join<ClothesItem, Brand> brandJoin = root.join("brand", JoinType.INNER);
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

    public Specification<ClothesItem> isGenderEquals(List<GridFilterRule> rules){
        return new Specification<ClothesItem>() {
            @Override
            public Predicate toPredicate(Root<ClothesItem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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