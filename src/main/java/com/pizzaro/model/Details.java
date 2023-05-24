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
public class Details {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private int quantity;
    private int price;
    @ManyToOne(fetch = FetchType.LAZY)
    private Orderings ordering;
    @ManyToOne(fetch = FetchType.LAZY)
    private Users owner;
    @OneToOne(fetch = FetchType.LAZY)
    private Products product;

    public Details(int quantity, int price) {
        this.quantity = quantity;
        this.price = price;
    }
}
