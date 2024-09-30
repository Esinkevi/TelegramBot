package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.model.Subscribers;
import com.skillbox.cryptobot.repository.SubscriberRepository;
import com.skillbox.cryptobot.service.CryptoCurrencyService;
import com.skillbox.cryptobot.service.getSubscribe.GetSubscribe;
import com.skillbox.cryptobot.utils.TextUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Service
@Slf4j
@AllArgsConstructor
public class GetSubscriptionCommand implements IBotCommand {

    private final GetSubscribe getSubscribe;

    @Override
    public String getCommandIdentifier() {
        return "get_subscription";
    }

    @Override
    public String getDescription() {
        return "Возвращает текущую подписку";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        Long telegramId = message.getChatId();
        Subscribers subscribers = getSubscribe.getSubscribers(telegramId);
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());
        if (subscribers.getPrice() == 0) {
            answer.setText("Активные подписки отсутствуют");
        } else {
            answer.setText("Вы подписаны на стоимость биткоина " + subscribers.getPrice() + " USD");
        }

        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            log.error("Ошибка возникла /get_price методе", e);
        }


    }


}

