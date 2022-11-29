package com.echobot.telegram_bot.main;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

public class Keyboard extends ReplyKeyboardMarkup {
    public Keyboard(){
        super();
    }

    public Keyboard(Status status){
        super();
        this.setResizeKeyboard(true); //подгоняем размер
        this.setOneTimeKeyboard(true); //скрываем после использования
        //Создаем список с рядами кнопок
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();

        switch (status){
            case MEETING:
                break;
            case PEEK_CATEGORY:
                keyboardRows.add(new KeyboardRow());
                keyboardRows.add(new KeyboardRow());
                keyboardRows.get(0).add(new KeyboardButton("Математика"));
                keyboardRows.get(0).add(new KeyboardButton("Программирование"));
                keyboardRows.get(1).add(new KeyboardButton("Физика"));
                keyboardRows.get(1).add(new KeyboardButton("Английский язык"));
                break;
            case PEEK_COURSE:
                keyboardRows.add(new KeyboardRow());
                keyboardRows.add(new KeyboardRow());
                keyboardRows.add(new KeyboardRow());
                keyboardRows.add(new KeyboardRow());
                keyboardRows.get(0).add(new KeyboardButton("Школа"));
                keyboardRows.get(1).add(new KeyboardButton("1ый курс"));
                keyboardRows.get(1).add(new KeyboardButton("2ой курс"));
                keyboardRows.get(2).add(new KeyboardButton("3ий курс"));
                keyboardRows.get(2).add(new KeyboardButton("4ый курс"));
                keyboardRows.get(3).add(new KeyboardButton("Магистратура"));
                keyboardRows.get(3).add(new KeyboardButton("Аспирантура"));
                break;
            case SENDING_WORK:
                break;
            case PEEKING_WORKER:
                break;
            case WAITING_WORK_COMPLETE:
                break;
            case WHO_ARE_U:
                keyboardRows.add(new KeyboardRow());
                keyboardRows.add(new KeyboardRow());
                keyboardRows.get(0).add(new KeyboardButton("Хочу у вас работать"));
                keyboardRows.get(1).add(new KeyboardButton("Хочу заказать работу у специалиста"));
                break;
            case SENDING_RESUME:
                break;
        }
        this.setKeyboard(keyboardRows);
    }

}
