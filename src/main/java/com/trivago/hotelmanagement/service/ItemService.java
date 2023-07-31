package com.trivago.hotelmanagement.service;

import com.trivago.hotelmanagement.config.ResponseExceptionHandler;
import com.trivago.hotelmanagement.config.multitenancy.TenantAwareService;
import com.trivago.hotelmanagement.model.Item;
import com.trivago.hotelmanagement.model.criteria.Criteria;
import com.trivago.hotelmanagement.model.criteria.ItemSpecification;
import com.trivago.hotelmanagement.model.enumeration.ReputationBadge;
import com.trivago.hotelmanagement.model.mapper.ItemMapper;
import com.trivago.hotelmanagement.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service class responsible to handle business logic for items.
 */
@Service
@TenantAwareService
public class ItemService {
    private final Logger LOGGER = LoggerFactory.getLogger(ResponseExceptionHandler.class);

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemMapper itemMapper;

    @CacheEvict(value = "items", allEntries = true)
    public Item createItem(Item item) {
        calculateBadge(item);
        Item createdItem =  itemRepository.save(item);
        LOGGER.info("Item created with ID: " + item.getId());
        return createdItem;
    }

    /**
     * Updates an item by given id
     * @param item item to be updated
     * @param id id to look for item
     * @return updated item
     */
    @CacheEvict(value = "items", allEntries = true)
    public Item updateItem(Item item, Long id) {
        Optional<Item> itemFound = itemRepository.findById(id);
        if(itemFound.isPresent()) {
            LOGGER.info("item found with id: " + item.getId() + ". Updating item.");
            calculateBadge(item);
            itemMapper.updateItemFromItem(item, itemFound.get());
            return itemRepository.save(itemFound.get());
        }
        return null;
    }

    /**
     * Delete an item by Id
     * @param itemId Id of item to be deleted.
     * @return true if item is deleted false if not found.
     */
    @CacheEvict(value = "items", allEntries = true)
    public boolean deleteItem(Long itemId) {
        if (itemRepository.existsById(itemId)) {
            LOGGER.info("item found with id: " + itemId + ". Deleting item.");
            itemRepository.deleteById(itemId);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Book a place on a specific accommodation
     * @param itemId ID of the accommodation.
     * @return null in case booking was successful else error message.
     */
    @CacheEvict(value = "items", allEntries = true)
    public String bookAccommodation(Long itemId) {
        Optional<Item> itemFound = itemRepository.findById(itemId);
        if(itemFound.isPresent()) {
            Item targetItem = itemFound.get();
            if (targetItem.getAvailability() != null && targetItem.getAvailability() > 0) {
                targetItem.setAvailability(targetItem.getAvailability() - 1);
                itemRepository.save(targetItem);
                return null;
            }
            LOGGER.info("No more space available in:  " + targetItem.getId());
            return "Accommodation not available for booking.";
        }
        LOGGER.info("No accommodation available with given ID:  " + itemId);
        return "Accommodation not found.";
    }

    /**
     * Fetch the list of all available items
     * @return list of items
     */
    @CachePut("items")
    public List<Item> fetchAllItems() {
        return itemRepository.findAll();
    }

    /**
     * Fetch items by given id
     * @param id ID of the item.
     * @return found item else null
     */
    @CachePut ("items")
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

    /**
     * Search item by given query parameters
     * @param name name of the item
     * @param city city item belongs to
     * @param reputationBadge color of the reputation badge
     * @return list of all items that meet criteria
     */
    @CachePut("items")
    public List<Item> findByCriteria(String name, String city, ReputationBadge reputationBadge) {
        LOGGER.info("Searching for items that meet the criteria name : {}, city: {}, reputationBadge: {}", name, city, reputationBadge);
        Specification itemSpecification = new ItemSpecification();
        if (name != null) {
            itemSpecification = itemSpecification.and(new ItemSpecification(new Criteria("name", "=", name)));
        }
        if (city != null) {
            itemSpecification = itemSpecification.and(new ItemSpecification(new Criteria("location.city", "=", city)));
        }
        if (reputationBadge != null) {
            itemSpecification = itemSpecification.and(new ItemSpecification(new Criteria("reputationBadge", "=", reputationBadge.name())));
        }
        return itemRepository.findAll(itemSpecification);
    }

    /**
     * Searches for items that meets the given criteria.
     * @param criteriaList list of all criteria
     * @return list of all items that meet criteria
     */
    @CachePut ("items")
    public List<Item> findByCriteriaList(List<Criteria> criteriaList) {
        LOGGER.info("Searching for items that meet provided criteria.");
        Specification itemSpecification = new ItemSpecification();
        for(Criteria criteria : criteriaList) {
            itemSpecification = itemSpecification.and(new ItemSpecification(criteria));
        }
        return itemRepository.findAll(itemSpecification);
    }
}
