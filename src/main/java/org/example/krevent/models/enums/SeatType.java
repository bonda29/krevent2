package org.example.krevent.models.enums;

import lombok.Getter;

@Getter
public enum SeatType {

    REGULAR_WOODEN(10.00),
    REGULAR(15.00),
    BALCONY(15.00),
    BALCONY_WOODEN(10.00);

    private final double price;

    SeatType(double price) {
        this.price = price;
    }
}
