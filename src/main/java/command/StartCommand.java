package command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public final class StartCommand extends AnonymizerCommand {

    private final AnonymousService mAnonymouses;

    // обязательно нужно вызвать конструктор суперкласса,
    // передав в него имя и описание команды
    public StartCommand(AnonymousService anonymouses) {
        super("start", "Начать использовать чат\n");
        mAnonymouses = anonymouses;
    }

    /**
     * реализованный метод класса BotCommand, в котором обрабатывается команда, введенная пользователем
     * @param absSender - отправляет ответ пользователю
     * @param user - пользователь, который выполнил команду
     * @param chat - чат бота и пользователя
     * @param strings - аргументы, переданные с командой
     */
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

     //   log.info(LogTemplate.COMMAND_PROCESSING.getTemplate(), user.getId(), getCommandIdentifier());

        StringBuilder sb = new StringBuilder();

        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());

         if (mAnonymouses.addAnonymous(new Anonymous(user, chat))) {
            sb.append("Привет, ").append(user.getUserName()).append("! Мы добавили Вас в чат лист!\n");
                   // .append("Пожалуйста используйте команду :\n'/set_name <ВАШЕ ИМЯ>'\n ");

        } else {
             sb.append("Вы уже добавлены в чат, если хотите можете изменить свое имя, используя команду (/set_name).");
        }

        sb.append("\n");
        sb.append("Получить новости ГАТИ :\n /news");

        sb.append("\n");
        sb.append("Пожалуйста используйте команду :\n /help");

        message.setText(sb.toString());
        execute(absSender, message, user);
    }
}