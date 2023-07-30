package com.trivago.hotelmanagement.service;

import com.trivago.hotelmanagement.model.Item;
import com.trivago.hotelmanagement.model.enumeration.ReputationBadge;
import com.trivago.hotelmanagement.model.mapper.ItemMapper;
import com.trivago.hotelmanagement.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemMapper itemMapper;

    public Item createItem(Item item) {
        calculateBadge(item);
        return itemRepository.save(item);
    }

    public Item updateItem(Item item, Long id) {
        Optional<Item> itemFound = itemRepository.findById(id);
        if(itemFound.isPresent()) {
            calculateBadge(item);
            itemMapper.updateItemFromItem(item, itemFound.get());
            return itemRepository.save(itemFound.get());
        }
        return null;
    }

    public boolean deleteItem(Long itemId) {
        if (itemRepository.existsById(itemId)) {
            itemRepository.deleteById(itemId);
            return true;
        } else {
            return false;
        }
    }

    public String bookAccommodation(Long itemId) {
        Optional<Item> itemFound = itemRepository.findById(itemId);
        if(itemFound.isPresent()) {
            Item targetItem = itemFound.get();
            if (targetItem.getAvailability() != null && targetItem.getAvailability() > 0) {
                targetItem.setAvailability(targetItem.getAvailability() - 1);
                itemRepository.save(targetItem);
                return null;
            }
            return "Accommodation not available for booking.";
        }
        return "Accommodation not found.";
    }


    public List<Item> fetchAllItems() {
        return itemRepository.findAll();
    }

    public Item findItemById(Long id) {
        return itemRepository.findById(id).orElse(null);
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
