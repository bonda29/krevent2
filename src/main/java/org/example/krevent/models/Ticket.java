package org.example.krevent.models;

import jakarta.persistence.*;
import lombok.*;
import org.example.krevent.models.abstracts.BaseEntity;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tickets")
public class Ticket extends BaseEntity {
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "ticket", optional = false, orphanRemoval = true)
    private HallSeat hallSeat;

    @Column(name = "price")
    private double price;

    @Column(name = "qr_code_image")
    private String qrCodeImage;

    @PrePersist
    protected void onCreate() {
        if (this.price < 0)
            this.price = this.hallSeat.getPrice();
    }
}
