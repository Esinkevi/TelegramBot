package com.skillbox.cryptobot.bot;

import com.skillbox.cryptobot.client.BinanceClient;
import com.skillbox.cryptobot.model.Subscribers;
import com.skillbox.cryptobot.service.costTracking.CostTracking;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PriceNotify {

    private static final String MESSAGE_TEMPLATE = "Пора покупать, стоимость биткоина ";

    private final AbsSender absSender;
    private final CostTracking costTracking;
    private final BinanceClient binanceClient;


    @Scheduled(fixedRateString = "#{T(java.util.concurrent.TimeUnit).MINUTES.toMillis(${crypto.frequency})}")
    public void notifyUsers() {
        double currentPrice = getBtcPrice();
        if (currentPrice <= 0) {
            log.warn("Current BTC price is invalid. Skipping notification.");
            return;
        }
        List<Subscribers> subscribersList = costTracking.searchSubscribers(currentPrice);
        for (Subscribers subscribers : subscribersList) {
            sendMessage(subscribers.getTelegramId(), currentPrice);
            costTracking.updateNotificationTime(subscribers);
        }

    }


    private void sendMessage(Long telegramId, double priceBtc) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(MESSAGE_TEMPLATE + priceBtc + " USD");
        sendMessage.setChatId(telegramId);
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Возникла ошибка в классе PriceNotify /sendMessage ", e);
        }
    }

    private double getBtcPrice() {
        try {
            return binanceClient.getBitcoinPrice();
        } catch (IOException e) {
            log.error("Error while getting price from binance", e);
        }
        return 0;
    }
}
