package com.trivago.hotelmanagement.model.criteria;

import com.trivago.hotelmanagement.model.Item;
import com.trivago.hotelmanagement.model.Location;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.Optional;

public class ItemSpecification implements Specification<Item> {
    private Criteria criteria;

    public ItemSpecification() {
    }

    public ItemSpecification(Criteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Specification<Item> and(Specification<Item> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Item> or(Specification<Item> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (criteria == null) return null;
        Join<Location, Item> itemLocation = root.join("location");

        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return criteriaBuilder.greaterThanOrEqualTo(
                    root.get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return criteriaBuilder.lessThanOrEqualTo(
                    root.get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("=")) {

            if (criteria.getKey().contains(".")) {
                String[] keyList = criteria.getKey().split("\\.");
                if (keyList[0].equals("location")) {
                    return criteriaBuilder.equal(itemLocation.get(keyList[1]), criteria.getValue());
                }
            } else if (root.get(criteria.getKey()).getJavaType().isEnum()) {
                Optional value = Arrays.stream(root.get(criteria.getKey()).getJavaType().getEnumConstants()).filter(en -> en.toString().equals(criteria.getValue())).findFirst();
                if (value.isPresent()) {
                    return criteriaBuilder.equal(root.get(criteria.getKey()), value.get());
                }
            } else {
                return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return criteriaBuilder.like(
                        root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else if (root.get(criteria.getKey()).getJavaType().isEnum()) {
                Optional value = Arrays.stream(root.get(criteria.getKey()).getJavaType().getEnumConstants()).filter(en -> en.toString().equals(criteria.getValue())).findFirst();
                if (value.isPresent()) {
                    return criteriaBuilder.equal(root.get(criteria.getKey()), value.get());
                }
            } else {
                return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }
}
