package com.skillbox.cryptobot.service.subscribe;

public interface ProcessSubscribe {

      void addSubscribe(Long telegramId, double price);

      void  unSubscribe(Long telegramId);
}
