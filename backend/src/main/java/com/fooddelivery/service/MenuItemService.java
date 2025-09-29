package com.fooddelivery.service;

import com.fooddelivery.entity.MenuItem;
import com.fooddelivery.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    public MenuItem createMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    public List<MenuItem> getMenuItemsByRestaurant(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }

    public List<MenuItem> getAvailableMenuItemsByRestaurant(Long restaurantId) {
        return menuItemRepository.findByRestaurantIdAndAvailableTrue(restaurantId);
    }

    public List<MenuItem> getMenuItemsByCategory(Long restaurantId, String category) {
        return menuItemRepository.findByRestaurantIdAndCategory(restaurantId, category);
    }

    public Optional<MenuItem> getMenuItemById(Long id) {
        return menuItemRepository.findById(id);
    }

    public MenuItem updateMenuItem(Long id, MenuItem menuItemDetails) {
        Optional<MenuItem> optionalMenuItem = menuItemRepository.findById(id);
        if (optionalMenuItem.isPresent()) {
            MenuItem menuItem = optionalMenuItem.get();
            if (menuItemDetails.getName() != null) menuItem.setName(menuItemDetails.getName());
            if (menuItemDetails.getDescription() != null) menuItem.setDescription(menuItemDetails.getDescription());
            if (menuItemDetails.getPrice() != null) menuItem.setPrice(menuItemDetails.getPrice());
            if (menuItemDetails.getImageUrl() != null) menuItem.setImageUrl(menuItemDetails.getImageUrl());
            if (menuItemDetails.getCategory() != null) menuItem.setCategory(menuItemDetails.getCategory());
            if (menuItemDetails.getAvailable() != null) menuItem.setAvailable(menuItemDetails.getAvailable());
            return menuItemRepository.save(menuItem);
        }
        return null;
    }

    public boolean deleteMenuItem(Long id) {
        if (menuItemRepository.existsById(id)) {
            menuItemRepository.deleteById(id);
            return true;
        }
        return false;
    }
}