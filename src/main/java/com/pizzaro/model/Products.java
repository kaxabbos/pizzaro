package com.pizzaro.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Products {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String name;
    private String photo;
    private int quantity;
    private int price;

    public Products(String name, String photo, int quantity, int price) {
        this.name = name;
        this.photo = photo;
        this.quantity = quantity;
        this.price = price;
    }
}
