package com.devpgm.springoauth2.repositoriy;

import com.devpgm.springoauth2.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findAllByMenuId(Long id);
}
