package com.skillbox.cryptobot.service.costTracking;

import com.skillbox.cryptobot.client.BinanceClient;
import com.skillbox.cryptobot.model.Subscribers;
import com.skillbox.cryptobot.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CostTrackingImpl implements CostTracking {

    private final SubscriberRepository subscriberRepository;

    @Value("${crypto.min-notify-interval}")
    private int minNotifyIntervalMinutes;

    @Override
    public List<Subscribers> searchSubscribers(double priceBtc) {
        return checkLastNotify(subscriberRepository.findByPriceLessThanEqual(priceBtc));
    }

    @Override
    public void updateNotificationTime(Subscribers subscribers) {
        subscribers.setLastNotificationTime(LocalDateTime.now());
        subscriberRepository.save(subscribers);
    }

    private List<Subscribers> checkLastNotify(List<Subscribers> byPriceLessThanEqual) {
        List<Subscribers> eligibleSubscribers = new ArrayList<>();
        for (Subscribers subscribers : byPriceLessThanEqual) {
            if (subscribers.getLastNotificationTime() == null || isTimeToNotify(subscribers.getLastNotificationTime())) {
                eligibleSubscribers.add(subscribers);
            }
        }
        return eligibleSubscribers;
    }

    private boolean isTimeToNotify(LocalDateTime lastNotificationTime) {
        Duration minInterval = Duration.ofMinutes(minNotifyIntervalMinutes);
        return Duration.between(lastNotificationTime, LocalDateTime.now()).compareTo(minInterval) > 0;
    }


}