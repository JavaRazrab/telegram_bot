package com.echobot.telegram_bot.entities;

import com.echobot.telegram_bot.main.Status;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "workers")
@Data
public class Worker{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;
    @Column(name = "chatId")
    private String chatId;
    @Column(name = "status")

    private Status status;
    private String surname;

    public Worker() {

    }
    public Worker(String chatId){
        this.chatId = chatId;
    }
    public Worker(Worker worker) {
        id = worker.id;
        chatId = worker.chatId;
        status = worker.status;
        name = worker.name;
        surname = worker.surname;
    }
}
