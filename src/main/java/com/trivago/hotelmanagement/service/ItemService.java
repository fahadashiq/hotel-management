package com.trivago.hotelmanagement.service;

import com.trivago.hotelmanagement.model.Item;
import com.trivago.hotelmanagement.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public Item createItem(Item item) {
        return itemRepository.save(item);
    }
}
