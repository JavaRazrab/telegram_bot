package com.echobot.telegram_bot;

import com.echobot.telegram_bot.entities.Manager;
import com.echobot.telegram_bot.entities.Worker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component("managerRepository")
public interface ManagerRepository extends CrudRepository<Manager, Integer> {
        Manager findByChatId(String chatId);
}
