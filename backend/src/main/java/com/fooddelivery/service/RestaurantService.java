package com.fooddelivery.service;

import com.fooddelivery.entity.Restaurant;
import com.fooddelivery.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Optional<Restaurant> getRestaurantById(Long id) {
        return restaurantRepository.findById(id);
    }

    public List<Restaurant> getRestaurantsByCategory(String category) {
        return restaurantRepository.findByCategory(category);
    }

    public List<Restaurant> searchRestaurants(String keyword) {
        return restaurantRepository.searchRestaurants(keyword);
    }

    public List<Restaurant> getTopRatedRestaurants() {
        return restaurantRepository.findTop5ByOrderByRatingDesc();
    }

    public Restaurant updateRestaurant(Long id, Restaurant restaurantDetails) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            if (restaurantDetails.getName() != null) restaurant.setName(restaurantDetails.getName());
            if (restaurantDetails.getDescription() != null) restaurant.setDescription(restaurantDetails.getDescription());
            if (restaurantDetails.getAddress() != null) restaurant.setAddress(restaurantDetails.getAddress());
            if (restaurantDetails.getPhone() != null) restaurant.setPhone(restaurantDetails.getPhone());
            if (restaurantDetails.getImageUrl() != null) restaurant.setImageUrl(restaurantDetails.getImageUrl());
            if (restaurantDetails.getCategory() != null) restaurant.setCategory(restaurantDetails.getCategory());
            if (restaurantDetails.getRating() != null) restaurant.setRating(restaurantDetails.getRating());
            if (restaurantDetails.getDeliveryTime() != null) restaurant.setDeliveryTime(restaurantDetails.getDeliveryTime());
            if (restaurantDetails.getDeliveryFee() != null) restaurant.setDeliveryFee(restaurantDetails.getDeliveryFee());
            return restaurantRepository.save(restaurant);
        }
        return null;
    }

    public boolean deleteRestaurant(Long id) {
        if (restaurantRepository.existsById(id)) {
            restaurantRepository.deleteById(id);
            return true;
        }
        return false;
    }
}