package com.fooddelivery.controller;

import com.fooddelivery.entity.MenuItem;
import com.fooddelivery.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/menu-items")
@CrossOrigin(origins = "http://localhost:3000")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @GetMapping("/restaurant/{restaurantId}")
    public List<MenuItem> getMenuItemsByRestaurant(@PathVariable Long restaurantId) {
        return menuItemService.getMenuItemsByRestaurant(restaurantId);
    }

    @GetMapping("/restaurant/{restaurantId}/available")
    public List<MenuItem> getAvailableMenuItems(@PathVariable Long restaurantId) {
        return menuItemService.getAvailableMenuItemsByRestaurant(restaurantId);
    }

    @GetMapping("/restaurant/{restaurantId}/category/{category}")
    public List<MenuItem> getMenuItemsByCategory(@PathVariable Long restaurantId, @PathVariable String category) {
        return menuItemService.getMenuItemsByCategory(restaurantId, category);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        Optional<MenuItem> menuItem = menuItemService.getMenuItemById(id);
        return menuItem.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public MenuItem createMenuItem(@Valid @RequestBody MenuItem menuItem) {
        return menuItemService.createMenuItem(menuItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable Long id, @Valid @RequestBody MenuItem menuItemDetails) {
        MenuItem updatedMenuItem = menuItemService.updateMenuItem(id, menuItemDetails);
        return updatedMenuItem != null ? ResponseEntity.ok(updatedMenuItem) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable Long id) {
        boolean deleted = menuItemService.deleteMenuItem(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}