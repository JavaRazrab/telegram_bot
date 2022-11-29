package com.echobot.telegram_bot.entities;

import com.echobot.telegram_bot.main.Status;
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
    protected String name;
    @Column(name = "chatId")
    protected String chatId;
    @Column(name = "status")
    protected Status status;
    @Column(name = "roleId")
    protected Role role;
    protected String surname;

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