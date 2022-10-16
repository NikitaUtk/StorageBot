package org.example.controller;

import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import lombok.var;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
@Log4j
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;

    //private static final Logger log = Logger.getLogger(TelegramBot.class);
    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        var originalMessage = update.getMessage();
        log.debug(originalMessage.getText());

        var responce = new SendMessage();
        responce.setChatId(originalMessage.getChatId().toString());
        responce.setText("Hello from bot");
        sendAnswerMessage(responce);
    }

    public void sendAnswerMessage(SendMessage message){
        if(message != null){
            try{
                execute(message);
            }catch(TelegramApiException e){
                log.error(e);
            }
        }
    }
}
