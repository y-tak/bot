package command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.stream.Stream;

public final class AnonymizerBot extends TelegramLongPollingCommandBot {

    private static final Logger LOG = LogManager.getLogger(AnonymizerBot.class);
   // private static final  LOGGER= java.util.logging.Logger.getLogger(AnonymizerBot.class.getName());
    // имя бота, которое мы указали при создании аккаунта у BotFather
    // и токен, который получили в результате
    private static final String BOT_NAME = "Y_tak_bot";
    private static final String BOT_TOKEN = "974487902:AAGU98V2pygrc-ZDVaw-yCeWKB2Ub54qGlI";

    private final AnonymousService mAnonymouses;


    public AnonymizerBot(DefaultBotOptions botOptions) throws IOException {

        super(botOptions, BOT_NAME);
        FileHandler fileHandler=new FileHandler("loggerExample.log");
          mAnonymouses = new AnonymousService();

        // регистрация всех кастомных команд

        register(new StartCommand(mAnonymouses));
      //  register(new SetNameCommand(mAnonymouses));
      //  register(new StopCommand(mAnonymouses));
     //    register(new MyNameCommand(mAnonymouses));
        register(new NewsCommand(mAnonymouses));
      //  register(new AnekdotCommand(mAnonymouses));

        HelpCommand helpCommand = new HelpCommand(this);
        register(helpCommand);
//-------------

        registerDefaultAction(((absSender, message) -> {
            SendMessage text = new SendMessage();
            text.setChatId(message.getChatId());
            text.setText(message.getText() + " неизвестная команда!");

            try {
                absSender.execute(text);
            } catch (TelegramApiException e) {
              //  LOG.error("Error while replying unknown command to user {}.", message.getFrom(), e);
            }

            helpCommand.execute(absSender, message.getFrom(), message.getChat(), new String[] {});


        }));


    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    // обработка сообщения не начинающегося с '/'
    @Override
    public void processNonCommandUpdate(Update update) {


        if (!update.hasMessage()) {
            throw new IllegalStateException("Update doesn't have a body!");
        }

        Message msg = update.getMessage();
        User user = msg.getFrom();

//        if (!canSendMessage(user, msg)) {
//            return;
//        }

        String clearMessage = msg.getText();
        String messageForUsers = String.format("%s:\n%s", mAnonymouses.getDisplayedName(user), msg.getText());

        SendMessage answer = new SendMessage();
        String answerMessage = "";
        // отправка ответа отправителю о том, что его сообщение получено
        answer.setText(clearMessage);
        answer.setChatId(msg.getChatId());


        answerMessage=handleAnswer(clearMessage);
        //System.out.println("answerMessage = " + answerMessage);

        if (answerMessage.equals(""))
        answerMessage=clearMessage;

        answer.setText(answerMessage);

        replyToUser(answer, user, answerMessage);




        // отправка сообщения всем остальным пользователям бота
//        answer.setText(messageForUsers);
//        Stream<Anonymous> anonymouses = mAnonymouses.anonymouses();
//        anonymouses.filter(a -> !a.getUser().equals(user))
//                .forEach(a -> {
//                    answer.setChatId(a.getChat().getId());
//                    sendMessageToUser(answer, a.getUser(), user);
//                });
    }

    private String handleAnswer(String clearMessage) {
        String answer="";

        if (clearMessage.contains("привет")||clearMessage.contains("Привет"))
            answer= "Здравствуйте, рад с Вами познакомиться";
       else if (clearMessage.contains("пока")||clearMessage.contains("Пока"))
            answer= "Прощайте";
        else if (clearMessage.contains("дела")||clearMessage.contains("Дела"))
            answer= "Дела хорошо, как ваши?";
        else if (clearMessage.contains("помоги")||clearMessage.contains("помощь")||clearMessage.contains("Помоги")||clearMessage.contains("Помощь"))
            answer= "Чем я могу вам помочь? вы можете воспользоваться помощником /help";
        else if (clearMessage.contains("выход"))
            answer= "Выйти из разгоовора по кнопке /stop";
        else if (clearMessage.contains("анекдот")||clearMessage.contains("шутка")||clearMessage.contains("Анекдот"))
            answer= "15 детей из многодетной семьи, уговорили наконец-то отца сбрить усы, которые сводят с ума их мамку..остальные по кнопке /anekdot";
        else if (clearMessage.contains("погода")||clearMessage.contains("Погод"))
            answer= "на деребасова хорошая погода брайтон бич опять идут дожди";
        else if (clearMessage.contains("Путин"))
            answer= "Путин - наш презедент";
        else if (clearMessage.contains("Медведев"))
            answer= "Медведев - экс президент, ныне премьер министр РФ";
        else if (clearMessage.contains("машина")|| clearMessage.contains("авто")||clearMessage.contains("Машина")||clearMessage.contains("Авто"))
            answer= "Интересуешься автомобилями. Ты - водитель? ";
        else if (clearMessage.contains("Да")|| clearMessage.contains("да"))
            answer= "Угадала ";
        else if (clearMessage.contains("Нет")|| clearMessage.contains("нет"))
            answer= "Может в другой раз повезет";
        else if (clearMessage.contains("еда")|| clearMessage.contains("рецепт")|| clearMessage.contains("блюдо")||clearMessage.contains("Блюдо")|| clearMessage.contains("Еда"))
            answer= "Блюдо дня -Шилпилдок- узбекский аналог бешбармака, казахского блюда из мяса и теста";
        else if (clearMessage.contains("Зовут")|| clearMessage.contains("зовут"))
            answer= "Меня зовут Геннадий. А Вас?";
        else if (clearMessage.contains("Хуй")|| clearMessage.contains("хуй"))
            answer= "у нас не матерятся?";
        else if (clearMessage.contains("Саша")|| clearMessage.contains("Катя"))
            answer= "Приятно познакомиться";
        else if (clearMessage.contains("Работа")|| clearMessage.contains("работа"))
            answer= "Работа, работа, да ну ее в болото";
        else if (clearMessage.contains("очистить")|| clearMessage.contains("Очистить"))
        {
            try {
                this.clearWebhook();
            } catch (TelegramApiRequestException e) {
                e.printStackTrace();
            }
            answer= "";
        }
        else if (clearMessage.contains("?"))
            answer= "хмм, А вы с какой целью интересуетесь?";

        return  answer;
    }


    @Override
    public void clearWebhook() throws TelegramApiRequestException {
        super.clearWebhook();
    }

    // несколько проверок, чтобы можно было отправлять сообщения другим пользователям
    private boolean canSendMessage(User user, Message msg) {

        SendMessage answer = new SendMessage();
        answer.setChatId(msg.getChatId());

        if (!msg.hasText() || msg.getText().trim().length() == 0) {

            answer.setText("You shouldn't send empty messages!");
            replyToUser(answer, user, msg.getText());
            return false;
        }

        if(!mAnonymouses.hasAnonymous(user)) {

            answer.setText("Firstly you should start bot! Use /start command!");
            replyToUser(answer, user, msg.getText());
            return false;
        }

        if (mAnonymouses.getDisplayedName(user) == null) {

            answer.setText("You must set a name before sending messages.\nUse '/set_name <displayed_name>' command.");
            replyToUser(answer, user, msg.getText());
            return false;
        }

        return true;
    }
///---------------------------------------------
    private void sendMessageToUser(SendMessage message, User receiver, User sender) {
        try {
            execute(message);

        } catch (TelegramApiException e) {

        }
    }
///-----------------------------------------------------------
    private void replyToUser(SendMessage message, User user, String messageText) {
        try {
            execute(message);

        } catch (TelegramApiException e) {

        }
    }
}