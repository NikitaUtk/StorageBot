package org.example.controller;

import lombok.extern.log4j.Log4j;
import org.example.service.UpdateProducer;
import org.example.utils.MessageUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.example.model.RabbitQueue.*;

@Component
@Log4j
public class UpdateController {
    private TelegramBot telegramBot;
    private final MessageUtils messageUtils;
    private final UpdateProducer updateProducer;

    public UpdateController(MessageUtils messageUtils, UpdateProducer updateProducer) {
        this.messageUtils = messageUtils;
        this.updateProducer = updateProducer;
    }

    public void registerBot(TelegramBot telegramBot){
        this.telegramBot = telegramBot;
    }

    public void processUpdate(Update update){
        if(update == null){
            log.error("Received update is null");
            return;
        }
        if(update.hasMessage()){
            DistributeMessageByType(update);
        }else {
            log.error("Unsupported message type is received: " + update);
        }
    }

    private void DistributeMessageByType(Update update) {
        var message = update.getMessage();
        if(message.hasText()){
            ProcessTextMessage(update);
        }else if (message.hasDocument()){
            ProcessDocMessage(update);
        }else if (message.hasPhoto()){
            ProcessPhotoMessage(update);
        }else{
            setUnsupportetMessageTypeView(update);
        }
    }

    private void setUnsupportetMessageTypeView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update, "Unsupported message type!");
        setView(sendMessage);
    }
    private void setFileIsReceivedView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update, "File received, is processed...");
        setView(sendMessage);
    }
    public void setView(SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }

    private void ProcessPhotoMessage(Update update) {
        updateProducer.producer(PHOTO_MESSAGE_UPDATE, update);
        setFileIsReceivedView(update);
    }

    private void ProcessDocMessage(Update update) {
        updateProducer.producer(DOC_MESSAGE_UPDATE, update);
        setFileIsReceivedView(update);
    }

    private void ProcessTextMessage(Update update) {
        updateProducer.producer(TEXT_MESSAGE_UPDATE, update);
    }
}
