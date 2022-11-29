package com.echobot.telegram_bot.main;

import com.echobot.telegram_bot.entities.Role;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;
    @Column(name = "chatId")
    private String chatId;
    @Column(name = "status")
    private Status status;
    @Column(name = "roleId")
    private Role role;
    private String surname;

    public User() {

    }
    public User(String chatId){
        this.chatId = chatId;
    }
    public User(User user) {
        id = user.id;
        chatId = user.chatId;
        status = user.status;
        name = user.name;
        surname = user.surname;
        role = user.role;
    }
}