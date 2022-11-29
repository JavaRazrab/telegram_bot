package com.echobot.telegram_bot.entities;

import com.echobot.telegram_bot.main.Status;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "managers")
@Data
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;
    @Column(name = "chatId")
    private String chatId;
    @Column(name = "status")
    private Status status;

    @Column(name = "role")
    private String role;

    public Manager() {

    }
    public Manager(String chatId){
        this.chatId = chatId;
    }
    public Manager(Manager manager) {
        id = manager.id;
        chatId = manager.chatId;
        status = manager.status;
        name = manager.name;
        role = manager.role;
    }
}
