package com.devpgm.springoauth2.controller;

import com.devpgm.springoauth2.entity.Menu;
import com.devpgm.springoauth2.entity.MenuItem;
import com.devpgm.springoauth2.entity.Restaurant;
import com.devpgm.springoauth2.repositoriy.MenuItemRepository;
import com.devpgm.springoauth2.repositoriy.MenuRepository;
import com.devpgm.springoauth2.repositoriy.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("restaurants")
public class RestaurantController {
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;

    @GetMapping
    @RequestMapping("/public/list")
    public List<Restaurant> getRestaurants() {
        return restaurantRepository.findAll();
    }

    @GetMapping
    @RequestMapping("/public/menu/{restaurantId}")
    public Menu getMenu(@PathVariable Long restaurantId) {
        Menu menu = menuRepository.findByRestaurantId(restaurantId);
        menu.setMenuItems(menuItemRepository.findAllByMenuId(menu.id));
        return menu;
    }

    @PostMapping // admin access
    public Restaurant createRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @PostMapping
    @RequestMapping("/menu") // manager can access
    public Menu createMenu(Menu menu) {
        menuRepository.save(menu);
        menu.getMenuItems().forEach(menuItem -> {
            menuItem.setId(menu.id);
            menuItemRepository.save(menuItem);
        });

        return menu;
    }

    @PutMapping // owner can access
    @RequestMapping("menu/item/{itemId}/{price}")
    public MenuItem updateMenuItem(@PathVariable Long itemId, @PathVariable BigDecimal price) {
        Optional<MenuItem> menuItem = menuItemRepository.findById(itemId);
        if (menuItem.isPresent()) {
        menuItem.get().setPrice(price);
            menuItemRepository.save(menuItem.get());
            return menuItem.get();
        }

        return menuItem.get();
    }
}
