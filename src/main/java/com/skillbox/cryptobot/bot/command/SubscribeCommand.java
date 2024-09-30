package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.service.CryptoCurrencyService;
import com.skillbox.cryptobot.service.subscribe.ProcessSubscribe;
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

/**
 * Обработка команды подписки на курс валюты
 */
@Service
@Slf4j
@AllArgsConstructor
public class SubscribeCommand implements IBotCommand {

    private final ProcessSubscribe processSubscribe;

    private final CryptoCurrencyService cryptoCurrencyService;

    @Override
    public String getCommandIdentifier() {
        return "subscribe";
    }

    @Override
    public String getDescription() {
        return "Подписывает пользователя на стоимость биткоина";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        if (isValidation(arguments)) {
            double price = Double.parseDouble(arguments[0]);
            Long telegramId = message.getChatId();
            processSubscribe.addSubscribe(telegramId, price);
            try {
                String text = "Текущая цена биткоина " + TextUtil.toString(cryptoCurrencyService.getBitcoinPrice()) + " USD";
                String text2 = "Новая подписка создана на стоимость " + price + " USD";
                sendMassage(text, message.getChatId(), absSender);
                sendMassage(text2, message.getChatId(), absSender);
            } catch (IOException e) {
                log.error("Error getPriceBitcoin", e);
            }
        } else {
            String validationErrorText = " Формат стоимости валюты указан не верно ";
            sendMassage(validationErrorText, message.getChatId(), absSender);
        }
    }

    private void sendMassage(String text, Long chatId, AbsSender absSender) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Error occurred in /subscribe command", e);
        }
    }

    private boolean isValidation(String[] arg) {
        return arg != null && arg.length > 0 && isNumeric(arg[0]);
    }

    private boolean isNumeric(String str) {
        return str != null && str.matches("\\d+(\\.\\d+)?");
    }
}