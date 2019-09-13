package command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public final class MyNameCommand extends AnonymizerCommand {

    private final AnonymousService mAnonymouses;

    public MyNameCommand(AnonymousService anonymouses) {
        super("my_name", "Показать свое имя в чате\n");
        mAnonymouses = anonymouses;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {


        StringBuilder sb = new StringBuilder();

        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());

        if (!mAnonymouses.hasAnonymous(user)) {

            sb.append("Тебя нет в чат листе' list! Отправь команду /start !");
         //   log.log(Level.getLevel(LogLevel.STRANGE.getValue()), "User {} is trying to execute '{}' without starting the bot.", user.getId(), getCommandIdentifier());

        } else if(mAnonymouses.getDisplayedName(user) == null) {

            sb.append("К сожалению ваc нет в Чате.\n  используйте команду для добавления:\n'/set_name &lt;displayed_name&gt;'");
         //   log.log(Level.getLevel(LogLevel.STRANGE.getValue()), "User {} is trying to execute '{}' without having a name.", user.getId(), getCommandIdentifier());

        } else {

      //      log.info("User {} is executing '{}'. Name is '{}'.", user.getId(), getCommandIdentifier(), mAnonymouses.getDisplayedName(user));
            sb.append("Твое текущее имя: ").append(mAnonymouses.getDisplayedName(user));
        }

        message.setText(sb.toString());
        execute(absSender, message, user);
    }
}