package com.echobot.telegram_bot.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
class Bot extends TelegramLongPollingBot {

    String BOT_TOKEN;
    String BOT_USERNAME;

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

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
            if(userRepository.findByChatId(update.getMessage().getChatId().toString()) == null){
            //if(!userRepo.haveUser(update.getMessage().getChatId().toString())){
                user = new User(update.getMessage().getChatId().toString());
                user.setStatus("changeName");
                userRepository.save(user);
                //userRepo.addNewUser(user);
                message.setText("How can i call u?");
            }else{
                user = userRepository.findByChatId(update.getMessage().getChatId().toString());
                switch (user.getStatus()){
                    case "changeName":
                        user.setName(update.getMessage().getText());
                        message.setText("Got it, "+user.getName());
                        user.setStatus("repeat");
                        break;
                    case "repeat":
                        message.setText(user.getName()+", "+update.getMessage().getText());
                        break;
                }
                userRepository.save(user);
            }
            try {
                execute(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}