package com.echobot.telegram_bot.main;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component("userRepository")
public interface UserRepository extends CrudRepository<User, Integer> {
        User findByChatId(String chatId);
}
