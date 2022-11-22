package com.echobot.telegram_bot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class UserRepo {

    static private List<User> users = new ArrayList<>();

    public boolean haveUser(String chatId){
        for (User user: users) {
            if(user.getChatId().equals(chatId))
                return true;
        }
        return false;
    }

    public void addNewUser(User user){
        users.add(user);
    }

    public User getUser(String chatId){
        for (User user: users) {
            if(user.getChatId().equals(chatId))
                return user;
        }
        return null;
    }

    public void changeUser(User changedUser){
        users.forEach(x->{
            if(x.getChatId()==changedUser.getChatId()){
                x.setName(changedUser.getName());
                x.setSurname(changedUser.getSurname());
                x.setRole(changedUser.getRole());
            }
        });
    }

}
