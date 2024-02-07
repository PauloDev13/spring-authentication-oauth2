package com.devpgm.springoauth2.repositoriy;

import com.devpgm.springoauth2.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Menu findByRestaurantId(Long id);
}
