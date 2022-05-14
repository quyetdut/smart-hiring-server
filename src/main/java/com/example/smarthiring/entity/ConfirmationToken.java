package com.smartdev.iresource.authentication.entity;

import com.smartdev.iresource.authentication.enums.ConfirmationType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "confirmation_tokens")
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expireAt;

    private LocalDateTime confirmedAt;

    @Enumerated(EnumType.STRING)
    private ConfirmationType confirmationType;

    @ManyToOne
    @JoinColumn(nullable = false,
            name = "user_id",
            referencedColumnName = "id")
    private User user;

    public ConfirmationToken(String token,
                             LocalDateTime createdAt,
                             LocalDateTime expireAt,
                             User user, ConfirmationType type) {
        this.token = token;
        this.createdAt = createdAt;
        this.expireAt = expireAt;
        this.user = user;
        this.confirmationType = type;
    }
}
