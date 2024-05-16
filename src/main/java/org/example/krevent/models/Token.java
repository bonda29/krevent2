package org.example.krevent.models;

import jakarta.persistence.*;
import lombok.*;
import org.example.krevent.models.abstracts.BaseEntity;
import org.example.krevent.models.enums.TokenType;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Token extends BaseEntity {
    @Column(unique = true)
    public String token;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;

    public boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;
}
