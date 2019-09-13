package command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public final class StopCommand extends AnonymizerCommand {

    private final AnonymousService mAnonymouses;

    public StopCommand(AnonymousService anonymouses) {
        super("stop", "Выйти из чата?\n");
        mAnonymouses = anonymouses;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

      //  log.info(LogTemplate.COMMAND_PROCESSING.getTemplate(), user.getId(), getCommandIdentifier());

        StringBuilder sb = new StringBuilder();

        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());

        if (mAnonymouses.removeAnonymous(user)) {
          //  log.info("User {} has been removed from users list!", user.getId());
            sb.append("Вы были удалены из листа. Пока!");
        } else {
        //    log.log(Level.getLevel(LogLevel.STRANGE.getValue()), "User {} is trying to execute '{}' without having executed 'start' before!", user.getId(), getCommandIdentifier());
            sb.append("Вы покинули чат лист. Пока!");
        }

        message.setText(sb.toString());
        execute(absSender, message, user);

           }
}