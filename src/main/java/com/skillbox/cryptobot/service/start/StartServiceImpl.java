package com.skillbox.cryptobot.service.start;

import com.skillbox.cryptobot.model.Subscribers;
import com.skillbox.cryptobot.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StartServiceImpl implements ProcessStart {

    private final SubscriberRepository subscriberRepository;

    @Override
    public void addNewUser(Long telegramId) {
        if (subscriberRepository.findByTelegramId(telegramId) == null) {
            Subscribers subscribers = new Subscribers();
            subscribers.setTelegramId(telegramId);

            subscriberRepository.save(subscribers);
            System.out.println("save db entity with id " + subscribers.getTelegramId());
        }
        System.out.println("this user already registered");
    }
}
