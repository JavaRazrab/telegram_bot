package com.echobot.telegram_bot;

import com.echobot.telegram_bot.entities.Client;
import com.echobot.telegram_bot.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component("clientRepository")
public interface ClientRepository extends CrudRepository<Client, Integer> {
        Client findByChatId(String chatId);
}
