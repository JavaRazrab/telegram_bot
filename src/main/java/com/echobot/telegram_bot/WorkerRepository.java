package com.echobot.telegram_bot;

import com.echobot.telegram_bot.entities.User;
import com.echobot.telegram_bot.entities.Worker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component("workerRepository")
public interface WorkerRepository extends CrudRepository<Worker, Integer> {
        Worker findByChatId(String chatId);
}
