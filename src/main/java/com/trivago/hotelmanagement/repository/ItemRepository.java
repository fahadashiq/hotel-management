package com.trivago.hotelmanagement.repository;

import com.trivago.hotelmanagement.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
