package com.trivago.hotelmanagement.repository;

import com.trivago.hotelmanagement.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {
    @Query(value = "from Item where id = :id")
    Optional<Item> findById(Long id);
}
