package command;


import entity.CourseRepository;
import java.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public final class NewsCommand extends AnonymizerCommand{



    public static final String DB_URL = "jdbc:sqlserver://192.168.0.7;databaseName=db1";
    public static final String DB_Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    private final AnonymousService mAnonymouses;

    public String[] newsblock;

    public NewsCommand(AnonymousService anonymouses) {
        super("news", "Получить новости ГАТИ\n");
        mAnonymouses = anonymouses;


}



    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        StringBuilder sb = new StringBuilder();
        LocalDate date = LocalDate.now(); // получаем текущую дату
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());
        sb.append("Новости на сегодня ").append(date);
//        if (mAnonymouses.addAnonymous(new Anonymous(user, chat))) {
//            sb.append("Hi, ").append(user.getUserName()).append(" Все что ты хотел знать будет длаее в новостях!\n")
//                    .append("пожалуйста подожди");
//        } else {
            sb.append(" от ГАТИ: ");
     //   }
        message.setText(sb.toString());
        execute(absSender, message, user);

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); //Проверяем наличие JDBC драйвера для работы с БД
          //  System.out.println("есть драйвер");
            Connection connection = DriverManager.getConnection(DB_URL, "sa", "438149qQ");//соединениесБД
          //  System.out.println("Соединение с СУБД выполнено.");
            String query = "SELECT * FROM [db1].[dbo].[news] where [title]='ВСЕ НОВОСТИ'";
            Statement statement = connection.createStatement();
            // выбираем данные с БД
            ResultSet rs = statement.executeQuery(query);
            String newbgody = "";
            int h = 0;
            while (rs.next()) {
                newbgody = rs.getString("new_body");
               // System.out.println("newbgody = " + newbgody);

                message.setText(newbgody);
                execute(absSender, message, user);
            }

            connection.close();       // отключение от БД
           // System.out.println("Отключение от СУБД выполнено.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // обработка ошибки  Class.forName
            System.out.println("JDBC драйвер для СУБД не найден!");
        } catch (SQLException e) {
            e.printStackTrace(); // обработка ошибок  DriverManager.getConnection
            System.out.println("Ошибка SQL !");
        }
       /// sb.append(" Это все новости на сегодня! ");
        message.setText(" Это все новости на сегодня!\n ");
        execute(absSender, message, user);


        message.setText("Вернуться в меню :\n /help ");
        execute(absSender, message, user);
    }
}
