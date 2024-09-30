package com.skillbox.cryptobot.service.getSubscribe;

import com.skillbox.cryptobot.model.Subscribers;
import com.skillbox.cryptobot.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetSubscribeImpl implements GetSubscribe {

    private final SubscriberRepository subscriberRepository;


    @Override
    public Subscribers getSubscribers(Long telegramId) {
        return subscriberRepository.findByTelegramId(telegramId);
    }
}
