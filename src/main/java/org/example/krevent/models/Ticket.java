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
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(mappedBy = "ticket", optional = false, orphanRemoval = true)
    private HallSeat hallSeat;

    @Column(name = "price")
    private double price;

    @Column(name = "qr_code_image")
    private String qrCodeImage;

    @PrePersist
    protected void onCreate() {
        this.price = this.hallSeat.getPrice();
    }
}
