package com.echobot.telegram_bot.entities;

import com.echobot.telegram_bot.main.Status;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "clients")
@Data
public class Client{
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

    public Client() {

    }
    public Client(String chatId){
        this.chatId = chatId;
    }
    public Client(Client client) {
        id = client.id;
        chatId = client.chatId;
        status = client.status;
        name = client.name;
        surname = client.surname;
    }
}
