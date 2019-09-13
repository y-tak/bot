package command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public final class AnekdotCommand extends AnonymizerCommand {

    private final AnonymousService mAnonymouses;

    public AnekdotCommand(AnonymousService anonymouses) {
        super("anekdot", "Анекдоты\n");
        mAnonymouses = anonymouses;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        //  log.info(LogTemplate.COMMAND_PROCESSING.getTemplate(), user.getId(), getCommandIdentifier());


        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());
        String am = "";

        for (int i = 0; i < 10; i++) {

           switch (i){
               case 1: am = "Случайно переключился на радио дача, услышал песни своей молодости, понял – это всё ";break;
               case 2: am = "За 20 лет своей жизни, я понял самое главное – тарелку после гречки нужно мыть сразу.";break;
               case 3: am = "Если вы купили часы без документов и коробки, а браслет немного в крови, то они могут быть и настоящие.";break;
               case 4: am = "Маленький мальчик, посмотрев найденную кассету с подписью “Клубничка”, понял, с каким трудом бабушке достается варенье.";break;
               case 5: am = "Эти подозрительные типы, похоже, уже разнюхали, что я недавно вылечился от паранойи. ";break;
               case 6: am = "Раньше младшие братья получали от старших старые игрушки. А теперь аккаунт от игрушки. ";break;
               case 7: am = "Даже и не знаю, что скажу своим детям, когда они спросят меня, почему я называю телевизор ящиком. ";break;
               case 8: am = "Дорогой, я на Почту России за посылкой! Пока меня не будет, воспитай нашего сына настоящим мужчиной, пожалуйста.";break;
               case 9: am = "Бесит, когда приходится поднимать свое царское величество в 6 утра по делам государственным.";break;
           }


            message.setText(am);
            execute(absSender, message, user);
            message.setText("******");
            execute(absSender, message, user);
        }
        message.setText("На этом ВСЁ! /help");
        execute(absSender, message, user);



    }

}