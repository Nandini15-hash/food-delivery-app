package com.fooddelivery.controller;

import com.fooddelivery.entity.*;
import com.fooddelivery.service.OrderService;
import com.fooddelivery.service.UserService;
import com.fooddelivery.service.RestaurantService;
import com.fooddelivery.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuItemService menuItemService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable Long userId) {
        return orderService.getOrdersByUser(userId);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<Order> getOrdersByRestaurant(@PathVariable Long restaurantId) {
        return orderService.getOrdersByRestaurant(restaurantId);
    }

    @GetMapping("/status/{status}")
    public List<Order> getOrdersByStatus(@PathVariable OrderStatus status) {
        return orderService.getOrdersByStatus(status);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        // Verify user exists
        Optional<User> user = userService.getUserById(orderRequest.getUserId());
        if (!user.isPresent()) {
            return ResponseEntity.badRequest().body("Error: User not found");
        }

        // Verify restaurant exists
        Optional<Restaurant> restaurant = restaurantService.getRestaurantById(orderRequest.getRestaurantId());
        if (!restaurant.isPresent()) {
            return ResponseEntity.badRequest().body("Error: Restaurant not found");
        }

        // Create order
        Order order = new Order(user.get(), restaurant.get(), orderRequest.getDeliveryAddress());
        order.setCustomerNotes(orderRequest.getCustomerNotes());

        // Create order items
        List<OrderItem> orderItems = orderRequest.getItems().stream()
                .map(itemRequest -> {
                    Optional<MenuItem> menuItem = menuItemService.getMenuItemById(itemRequest.getMenuItemId());
                    if (menuItem.isPresent()) {
                        return new OrderItem(order, menuItem.get(), itemRequest.getQuantity());
                    }
                    return null;
                })
                .filter(item -> item != null)
                .collect(Collectors.toList());

        if (orderItems.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: No valid menu items in order");
        }

        Order createdOrder = orderService.createOrder(order, orderItems);
        return ResponseEntity.ok(createdOrder);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestBody OrderStatus status) {
        Order updatedOrder = orderService.updateOrderStatus(id, status);
        return updatedOrder != null ? ResponseEntity.ok(updatedOrder) : ResponseEntity.notFound().build();
    }

    // Request DTOs
    public static class OrderRequest {
        private Long userId;
        private Long restaurantId;
        private String deliveryAddress;
        private String customerNotes;
        private List<OrderItemRequest> items;

        // Getters and Setters
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }

        public Long getRestaurantId() { return restaurantId; }
        public void setRestaurantId(Long restaurantId) { this.restaurantId = restaurantId; }

        public String getDeliveryAddress() { return deliveryAddress; }
        public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

        public String getCustomerNotes() { return customerNotes; }
        public void setCustomerNotes(String customerNotes) { this.customerNotes = customerNotes; }

        public List<OrderItemRequest> getItems() { return items; }
        public void setItems(List<OrderItemRequest> items) { this.items = items; }
    }

    public static class OrderItemRequest {
        private Long menuItemId;
        private Integer quantity;

        // Getters and Setters
        public Long getMenuItemId() { return menuItemId; }
        public void setMenuItemId(Long menuItemId) { this.menuItemId = menuItemId; }

        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }
}