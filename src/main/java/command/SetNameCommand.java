package command;


import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public final class SetNameCommand extends AnonymizerCommand {

    private final AnonymousService mAnonymouses;

    public SetNameCommand(AnonymousService anonymouses) {
        super("set_name", "Установить имя в чате \n");
        mAnonymouses = anonymouses;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings)
    {

        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());

        if (!mAnonymouses.hasAnonymous(user)) {
             message.setText("Вы не идентифицированы, начните сначала /start'!");
            execute(absSender, message, user);
            return;
        }

        String displayedName = getName(strings);

//        if (displayedName == null) {
//            message.setText("Ваше имя не может быть пустым! Установите");
//            execute(absSender, message, user);
//            return;
//        }

        StringBuilder sb = new StringBuilder();

        if (mAnonymouses.setUserDisplayedName(user, displayedName)) {

            if (mAnonymouses.getDisplayedName(user) == null) {
                mAnonymouses.setUserDisplayedName(user,"Джон");
                sb.append("Подключили: '").append(displayedName).append("'.Ты можешь отправлять сообщения!");
            } else {
                sb.append("Твое новое имя  на экране: '").append(displayedName).append("'.");
            }
        } else {
            sb.append("Имя ").append(displayedName).append(" уже используется! Поменяйте на другое!");
        }

        message.setText(sb.toString());
        execute(absSender, message, user);
    }

    private String getName(String[] strings) {

        if (strings == null || strings.length == 0) {
            return null;
        }

        String name = String.join(" ", strings);
        return name.replaceAll(" ", "").isEmpty() ? null : name;
    }
}
