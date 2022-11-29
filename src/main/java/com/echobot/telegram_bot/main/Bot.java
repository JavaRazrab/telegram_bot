package com.echobot.telegram_bot.main;

import com.echobot.telegram_bot.ClientRepository;
import com.echobot.telegram_bot.ManagerRepository;
import com.echobot.telegram_bot.UserRepository;
import com.echobot.telegram_bot.WorkerRepository;
import com.echobot.telegram_bot.entities.Client;
import com.echobot.telegram_bot.entities.Manager;
import com.echobot.telegram_bot.entities.Role;
import com.echobot.telegram_bot.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
class Bot extends TelegramLongPollingBot {

    private Keyboard keyboard;
    private Update update;
    private SendMessage message;
    String BOT_TOKEN;
    String BOT_USERNAME;

    @Autowired
    @Qualifier("workerRepository")
    private WorkerRepository workerRepository;
    @Autowired
    @Qualifier("clientRepository")
    private ClientRepository clientRepository;
    @Autowired
    @Qualifier("managerRepository")
    private ManagerRepository managerRepository;
    /*@Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;*/

    private Manager getBossAccount(String chatId){
        Manager manager = new Manager();
        manager.setRole("Boss");
        manager.setName("Сергей Михайлович");
        manager.setChatId(chatId);
        manager.setStatus(Status.MANAGERS_MENU);
        return manager;
    }

    private void makeAnswerForClient(){
        Client client = clientRepository.findByChatId(update.getMessage().getChatId().toString());
        switch (client.getStatus()){
            case MEETING:
                if(update.getMessage().getText().equals("root root")){
                    Manager boss = getBossAccount(update.getMessage().getChatId().toString());
                    managerRepository.save(boss);
                    message.setText("Не признал, "+boss.getName()+". Рад вас видеть, уже внес ваши данные в бд.");
                    keyboard = new Keyboard(boss.getStatus());
                }else{
                    client.setName(update.getMessage().getText());
                    client.setStatus(Status.WHO_ARE_U);
                    message.setText("Приятно познакомиться, "+client.getName()+"!\nЧем я могу помочь?");
                }
                break;
            case WHO_ARE_U:
                switch (update.getMessage().getText()){
                    case "Хочу у вас работать":
                        message.setText("Отлично, мы всегда рады новым работникам, ...та-та-та..., " +
                                "отправьте нам свое резюме в \"такой-то\" форме и мы рассмотрим его в ближайшее время");
                        client.setStatus(Status.SENDING_RESUME);
                        break;
                    case "Хочу заказать работу у специалиста":
                        message.setText("Отлично! Выберите, в какой сфере вам необходима помощь из предложенных");
                        client.setStatus(Status.PEEK_CATEGORY);
                        break;
                    default:
                        message.setText("Выберите один из предложенных вариантов");
                        break;
                }
                break;
            case PEEK_CATEGORY:
                message.setText("Теперь скажите нам, на каком курсе вы учитесь");
                client.setStatus(Status.PEEK_COURSE);
                //maybe without switch. Just change 'category' in 'works' on getMessage() (if valid (set?))
                switch (update.getMessage().getText()){
                    case "Математика":
                        //code about changing category in DB 'works' by clientId
                        break;
                    case "Программирование":
                        //code about changing category in DB 'works' by clientId
                        break;
                    case "Физика":
                        //code about changing category in DB 'works' by clientId
                        break;
                    case "Английский язык":
                        //code about changing category in DB 'works' by clientId
                        break;
                    default:
                        message.setText("Выберите один из предложенных вариантов");
                        break;
                }
                break;
            case PEEK_COURSE:
                message.setText("Теперь вышлите мне само задание в формате 'таком-то'. " +
                        "Так же отправьте мне текстом такие детали заказа как: ...\n(Дальше не написал, можешь ничего не крепить)");
                client.setStatus(Status.SENDING_WORK);
                //maybe without switch. Just change 'category' in 'works' on getMessage() (if valid (set?))
                switch (update.getMessage().getText()){
                    case "Школа":
                        //code about changing category in DB 'works' by clientId
                        break;
                    case "1ый курс":
                        //code about changing category in DB 'works' by clientId
                        break;
                    case "2ой курс":
                        //code about changing category in DB 'works' by clientId
                        break;
                    case "3ий курс":
                        //code about changing category in DB 'works' by clientId
                        break;
                    case "4ый курс":
                        //code about changing category in DB 'works' by clientId
                        break;
                    case "Магистратура":
                        //code about changing category in DB 'works' by clientId
                        break;
                    case "Аспирантура":
                        //code about changing category in DB 'works' by clientId
                        break;
                    default:
                        message.setText("Выберите один из предложенных вариантов");
                        break;
                }
                break;
            case SENDING_WORK:
            case SENDING_RESUME:
                message.setText("Функционал в разработке...");
                break;
        }
        Keyboard keyboard = new Keyboard(client.getStatus());
        message.setReplyMarkup(keyboard);
        clientRepository.save(client);
    }

    private void makeAnswerForWorker(){
        message.setText("Функционал в разработке...");
    }

    private void makeAnswerForManager(){
        message.setText("Функционал в разработке...");
    }

    //private final String adminPassword = "root";

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
        this.update = update;
        if(update.hasMessage() && update.getMessage().hasText()){
            message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            if(managerRepository.findByChatId(update.getMessage().getChatId().toString()) != null)
                makeAnswerForManager();
            else if (workerRepository.findByChatId(update.getMessage().getChatId().toString()) != null)
                makeAnswerForWorker();
            else if (clientRepository.findByChatId(update.getMessage().getChatId().toString()) != null)
                makeAnswerForClient();
            else{
                Client client = new Client(update.getMessage().getChatId().toString());
                client.setStatus(Status.MEETING);
                clientRepository.save(client);
                message.setText("Привет!\nКак я могу к тебе обращаться?");
            }
            try {
                execute(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}