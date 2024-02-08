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

    private Boolean active;

    private Long restaurantId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "menuId")
    private List<MenuItem> menuItems;

}
