package com.skillbox.cryptobot.service.subscribe;

import com.skillbox.cryptobot.model.Subscribers;
import com.skillbox.cryptobot.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscribeServiceImpl implements ProcessSubscribe {

    private final SubscriberRepository subscriberRepository;

    @Override
    public void addSubscribe(Long telegramId, double price) {
        Subscribers subscribers = subscriberRepository.findByTelegramId(telegramId);
        subscribers.setPrice(price);
        subscriberRepository.save(subscribers);
        System.out.println("price install");
    }

    @Override
    public void unSubscribe(Long telegramId) {
        Subscribers subscribers = subscriberRepository.findByTelegramId(telegramId);
        subscribers.setPrice(0);
        subscriberRepository.save(subscribers);
    }
}
