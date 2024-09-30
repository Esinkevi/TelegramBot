package com.skillbox.cryptobot.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@Data
@Entity
@Table(name = "subscribers")
public class Subscribers {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @Column(name = "telegram_id", nullable = false)
    private Long telegramId;

    @Column(name = "price", nullable = true)
    private double price;

    @Column(name = "last_notification_time")
    private LocalDateTime lastNotificationTime;
}
