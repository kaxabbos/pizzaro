package com.pizzaro.model;

import com.pizzaro.model.enums.Status;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Orderings {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String date;
    private int quantity;
    private int price;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToMany(mappedBy = "ordering", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Details> details = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    private Users owner;
    @ManyToOne(fetch = FetchType.LAZY)
    private Users manager;

    public Orderings(Users owner, String date) {
        this.owner = owner;
        this.date = date;
        this.status = Status.WAITING;
    }
}
