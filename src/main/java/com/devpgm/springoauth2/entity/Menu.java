package com.devpgm.springoauth2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "restaurant_id")
    private Long restaurantId;

    private Boolean active;

    @Transient
    @OneToMany
    private List<MenuItem> menuItems;

}
