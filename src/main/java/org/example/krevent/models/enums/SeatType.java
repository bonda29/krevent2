package org.example.krevent.models.enums;

import lombok.Getter;

@Getter
public enum SeatType {

    WOODEN_FRONT(10.00),
    REGULAR_FRONT(15.00),
    REGULAR_BACK(15.00),
    BALCONY_FRONT(15.00),
    BALCONY_BACK(10.00),
    BALCONY_WOODEN_BACK(10.00);

    private final double price;

    SeatType(double price) {
        this.price = price;
    }
}
