package com.devpgm.springoauth2.repositoriy;

import com.devpgm.springoauth2.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
