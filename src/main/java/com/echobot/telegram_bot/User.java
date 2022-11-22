package com.echobot.telegram_bot;

import lombok.Data;

@Data
public class User {
    private String name, surname, chatId;

    private String status;
    private Role role;

    public User(String chatId){
        this.chatId = chatId;
    }
    public User(User user) {
        status = user.status;
        name = user.name;
        surname = user.surname;
        role = user.role;
    }
}
