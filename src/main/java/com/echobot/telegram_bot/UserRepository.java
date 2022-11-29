package com.echobot.telegram_bot;

import com.echobot.telegram_bot.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

//@Component("userRepository")
public interface UserRepository {//extends CrudRepository<User, Integer> {
        User findByChatId(String chatId);
}
