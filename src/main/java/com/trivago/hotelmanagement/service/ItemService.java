package com.trivago.hotelmanagement.service;

import com.trivago.hotelmanagement.model.Item;
import com.trivago.hotelmanagement.model.ReputationBadge;
import com.trivago.hotelmanagement.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public Item createItem(Item item) {
        calculateBadge(item);
        return itemRepository.save(item);
    }

    private void calculateBadge(Item item) {
        if (item.getReputation() != null) {
            if(item.getReputation().compareTo(BigDecimal.valueOf(501)) == -1) {
                item.setReputationBadge(ReputationBadge.RED);
            } else if (item.getReputation().compareTo(BigDecimal.valueOf(800)) == -1) {
                item.setReputationBadge(ReputationBadge.YELLOW);
            } else {
                item.setReputationBadge(ReputationBadge.GREEN);
            }
        }
    }
}
