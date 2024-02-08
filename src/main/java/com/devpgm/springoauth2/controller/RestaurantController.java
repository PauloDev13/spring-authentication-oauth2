package com.devpgm.springoauth2.controller;

import com.devpgm.springoauth2.entity.Menu;
import com.devpgm.springoauth2.entity.MenuItem;
import com.devpgm.springoauth2.entity.Restaurant;
import com.devpgm.springoauth2.repositoriy.MenuItemRepository;
import com.devpgm.springoauth2.repositoriy.MenuRepository;
import com.devpgm.springoauth2.repositoriy.RestaurantRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("restaurants")
@SecurityRequirement(name = "Keycloak")
public class RestaurantController {
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;

    @GetMapping // public API
    @RequestMapping("/public/list")
    public List<Restaurant> getRestaurants() {
        return restaurantRepository.findAll();
    }

    @GetMapping // public API
    @RequestMapping("/public/menu/{restaurantId}")
    public Menu getMenu(@PathVariable Long restaurantId) {
        Menu menu = menuRepository.findByRestaurantId(restaurantId);
        menu.setMenuItems(menuItemRepository.findAllByMenuId(menu.id));
        return menu;
    }

    @PostMapping // admin can access
    @PreAuthorize("hasRole('ADMIN')")
    public Restaurant createRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @PostMapping
    @RequestMapping("/menu") // manager can access
    @PreAuthorize("hasRole('USER')")
    public Menu createMenu(@RequestBody Menu menu) {
        Menu savedMenu = menuRepository.save(menu);
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.forEach(menuItem -> {
            menuItem.setMenuId(savedMenu.id);
            menuItemRepository.save(menuItem);
        });

        return savedMenu;
    }

    @PutMapping // owner can access
    @RequestMapping("menu/item/{itemId}/{price}")
    @PreAuthorize("hasRole('GUEST')")
    public MenuItem updateMenuItem(@PathVariable Long itemId, @PathVariable BigDecimal price) {
        Optional<MenuItem> menuItem = menuItemRepository.findById(itemId);
        menuItem.get().setPrice(price);
        menuItemRepository.save(menuItem.get());
        return menuItem.get();
    }
}
