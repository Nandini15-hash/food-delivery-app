package com.fooddelivery.repository;

import com.fooddelivery.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByCategory(String category);
    
    @Query("SELECT r FROM Restaurant r WHERE r.name LIKE %:keyword% OR r.description LIKE %:keyword%")
    List<Restaurant> searchRestaurants(@Param("keyword") String keyword);
    
    List<Restaurant> findTop5ByOrderByRatingDesc();
}