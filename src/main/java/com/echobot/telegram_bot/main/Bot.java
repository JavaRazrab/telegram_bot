package com.echobot.telegram_bot.main;

import com.echobot.telegram_bot.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Component
class Bot extends TelegramLongPollingBot {

    Keyboard keyboard;

    String BOT_TOKEN;
    String BOT_USERNAME;

    //private final String adminPassword = "root";

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

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
            User user;
            if(userRepository.findByChatId(update.getMessage().getChatId().toString()) == null){
                user = new User(update.getMessage().getChatId().toString());
                user.setStatus(Status.MEETING);
                userRepository.save(user);
                message.setText("Привет!\nКак я могу к тебе обращаться?");
            }else{
                user = userRepository.findByChatId(update.getMessage().getChatId().toString());
                keyboard = new Keyboard(user.getStatus());
                switch (user.getStatus()){
                    case MEETING:
                        user.setName(update.getMessage().getText());
                        user.setStatus(Status.WHO_ARE_U);
                        message.setText("Приятно познакомиться, "+user.getName()+"!\nЧем я могу помочь?");
                        break;
                    case WHO_ARE_U:
                        switch (update.getMessage().getText()){
                            case "Хочу у вас работать":
                                message.setText("Отлично, мы всегда рады новым работникам, ...та-та-та..., " +
                                        "отправьте нам свое резюме в \"такой-то\" форме и мы рассмотрим его в ближайшее время");
                                user.setRole(Role.WORKER);
                                user.setStatus(Status.SENDING_RESUME);
                                break;
                            case "Хочу заказать работу у специалиста":
                                message.setText("Отлично! Выберите, в какой сфере вам необходима помощь из предложенных:\n" +
                                        "1) Математика\n2) Программирование\n 3) Физика\n 4) Английский язык\n(Пока что кнопок нет, отправь мне цифру)");

                                user.setRole(Role.CLIENT);
                                user.setStatus(Status.PEEK_CATEGORY);
                                break;
                            default:
                                message.setText("Выберите один из предложенных вариантов");
                                break;
                        }
                        break;
                }
                message.setReplyMarkup(keyboard);
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