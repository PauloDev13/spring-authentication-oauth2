package com.devpgm.springoauth2.repositoriy;

import com.devpgm.springoauth2.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByRestaurantId(Long id);
}
