package com.trivago.hotelmanagement.service;

import com.trivago.hotelmanagement.model.Item;
import com.trivago.hotelmanagement.model.enumeration.ReputationBadge;
import com.trivago.hotelmanagement.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @Test
    public void testBadgeIsCalculatedCorrectlyOnItemSave() {
        Item item = new Item();
        item.setReputation(BigDecimal.valueOf(900));
        when(itemRepository.save(item)).thenReturn(item);
        itemService.createItem(item);
        assertEquals(ReputationBadge.GREEN, item.getReputationBadge());
    }

    @Test
    public void testAvailabilityDecreasesOnBookingAccommodation() {
        Item item = new Item();
        item.setAvailability(5);
        when(itemRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(item));
        assertNull(itemService.bookAccommodation(Long.valueOf(1)));
        assertEquals(4, item.getAvailability());
    }

    @Test
    public void testAccommodationNoAvailableForBooking() {
        Item item = new Item();
        item.setAvailability(0);
        when(itemRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(item));
        assertEquals("Accommodation not available for booking.", itemService.bookAccommodation(Long.valueOf(1)));
        assertEquals(0, item.getAvailability());
    }

}