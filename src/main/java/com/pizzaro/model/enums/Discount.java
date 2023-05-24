package com.pizzaro.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Getter
public enum Discount {
    A3714(0.8),
    S5927(0.9),
    N2649(0.85);

    private final double discount;


}

