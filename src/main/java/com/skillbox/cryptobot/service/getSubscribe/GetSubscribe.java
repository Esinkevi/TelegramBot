package com.skillbox.cryptobot.service.getSubscribe;

import com.skillbox.cryptobot.model.Subscribers;

public interface GetSubscribe {

    Subscribers getSubscribers(Long telegramId);
}
