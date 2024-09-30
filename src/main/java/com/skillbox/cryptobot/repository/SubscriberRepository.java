package com.skillbox.cryptobot.repository;

import com.skillbox.cryptobot.model.Subscribers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscribers, UUID> {

    Subscribers findByTelegramId(Long telegramId);


    List<Subscribers> findByPriceLessThanEqual(double priceBtc);
}
