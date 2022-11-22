package com.echobot.telegram_bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
class Bot extends TelegramLongPollingBot {

    String BOT_TOKEN;
    String BOT_USERNAME;

    UserRepo userRepo = new UserRepo();

    Bot(@Value("${bot.BOT_TOKEN}") String BOT_TOKEN, @Value("${bot.BOT_USERNAME}") String BOT_USERNAME) {
        this.BOT_TOKEN = BOT_TOKEN;
        this.BOT_USERNAME = BOT_USERNAME;
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            //message.setReplyToMessageId(update.getMessage().getMessageId());
            User user;
            if(!userRepo.haveUser(update.getMessage().getChatId().toString())){
                user = new User(update.getMessage().getChatId().toString());
                user.setStatus("changeName");
                userRepo.addNewUser(user);
                message.setText("How can i call u?");
            }else{
                user = userRepo.getUser(update.getMessage().getChatId().toString());
                switch (user.getStatus()){
                    case "changeName":
                        user.setName(update.getMessage().getText());
                        message.setText("Got it, "+userRepo.getUser(update.getMessage().getChatId().toString()).getName());
                        user.setStatus("repeat");
                        break;
                    case "repeat":
                        message.setText(userRepo.getUser(update.getMessage().getChatId().toString()).getName()+", "+update.getMessage().getText());
                        break;
                }
            }
            try {
                execute(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}