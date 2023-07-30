package com.trivago.hotelmanagement.controller;

import com.trivago.hotelmanagement.model.Error;
import com.trivago.hotelmanagement.model.Item;
import com.trivago.hotelmanagement.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {
    @Autowired
    private ItemService itemService;

    @Operation(summary = "Create a new Item.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the newly created item.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema( implementation = Item.class))}),
            @ApiResponse(responseCode = "400", description = "Returns a list of validation errors.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema( schema = @Schema (implementation = Error.class)))})
    })
    @RequestMapping(path = "/item", method = RequestMethod.POST)
    public ResponseEntity<Item> createItem(@Valid @RequestBody Item item) {
        return ResponseEntity.ok().body(itemService.createItem(item));
    }

    @Operation(summary = "Get list of all available items.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a list of all available items.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema( schema = @Schema (implementation = Item.class)))})
    })
    @RequestMapping(path = "/items", method = RequestMethod.GET)
    public ResponseEntity<List<Item>> createItem() {
        return ResponseEntity.ok().body(itemService.fetchAllItems());
    }

    @Operation(summary = "Get an item by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the item found.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema( implementation = Item.class))}),
            @ApiResponse(responseCode = "404", description = "Error in case item doesn't exist.")
    })
    @RequestMapping(path = "/item/{itemId}", method = RequestMethod.GET)
    public ResponseEntity<Item> createItem(@Parameter(description = "Item id for the accommodation.", required = true) @PathVariable Long itemId) {
        Item item = itemService.findItemById(itemId);
        if (item != null) {
            return ResponseEntity.ok().body(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Update an existing item by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the updated item.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema( implementation = Item.class))}),
            @ApiResponse(responseCode = "400", description = "Returns a list of validation errors.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema( schema = @Schema (implementation = Error.class)))}),
            @ApiResponse(responseCode = "404", description = "Error in case item doesn't exist.")
    })
    @RequestMapping(path = "/item/{itemId}", method = RequestMethod.PUT)
    public ResponseEntity<Item> updateItem(@Valid @RequestBody Item item, @Parameter(description = "Item id for the accommodation.", required = true) @PathVariable Long itemId) {
        Item udatedItem = itemService.updateItem(item, itemId);
        if (udatedItem != null) {
            return ResponseEntity.ok().body(udatedItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete an item by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item deleted successfully."),
            @ApiResponse(responseCode = "204", description = "Item doesn't exist.")
    })
    @RequestMapping(path = "/item/{itemId}", method = RequestMethod.DELETE)
    public ResponseEntity updateItem(@Parameter(description = "Item id for the accommodation.", required = true) @PathVariable Long itemId) {
        if (itemService.deleteItem(itemId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Book a place in requested accommodation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accommodation booked successfully."),
            @ApiResponse(responseCode = "400", description = "In case either accommodation not found or no place exists.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema( implementation = Error.class))}),
    })
    @RequestMapping(path = "/booking/{itemId}", method = RequestMethod.POST)
    public ResponseEntity bookAccommodation(@Parameter(description = "Item id for the accommodation.", required = true) @PathVariable Long itemId) {
        String errorMessage = itemService.bookAccommodation(itemId);
        if (errorMessage == null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error(errorMessage, null));
    }

}
