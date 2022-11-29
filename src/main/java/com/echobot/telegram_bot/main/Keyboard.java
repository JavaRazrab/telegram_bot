package com.echobot.telegram_bot.main;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

public class Keyboard extends ReplyKeyboardMarkup {
    public Keyboard(){
    }

    public Keyboard(Status status){
        this.setResizeKeyboard(true); //подгоняем размер
        this.setOneTimeKeyboard(false); //скрываем после использования
        //Создаем список с рядами кнопок
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        switch (status){
            case MEETING:
                break;
            case PEEK_CATEGORY:
                break;
            case PEEK_COURSE:
                break;
            case SENDING_WORK:
                break;
            case PEEKING_WORKER:
                break;
            case WAITING_WORK_COMPLETE:
                break;
            case WHO_ARE_U:
                KeyboardRow keyboardRow1 = new KeyboardRow(), keyboardRow2 = new KeyboardRow();
                keyboardRows.add(keyboardRow1);
                keyboardRows.add(keyboardRow2);
                keyboardRow1.add(new KeyboardButton("Хочу у вас работать"));
                keyboardRow2.add(new KeyboardButton("Хочу заказать работу у специалиста"));
                this.setKeyboard(keyboardRows);
                break;
            case SENDING_RESUME:
                break;
        }
    }

}
