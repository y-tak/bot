
import command.AnonymizerBot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;



//@SpringBootApplication
public final class BotInitializer {

    private static final Logger LOG = LogManager.getLogger(BotInitializer.class);
    //

    private static final String PROXY_HOST = "138.197.219.240";
    private static final int PROXY_PORT = 48305;

    public static void main(String[] args) {

        try {

         //   SpringApplication.run(BotInitializer.class, args);

          //  LOG.info("Initializing API context...");
            ApiContextInitializer.init();

            TelegramBotsApi botsApi = new TelegramBotsApi();

           // LOG.info("Configuring bot options...");
            DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

            botOptions.setProxyHost(PROXY_HOST);
            botOptions.setProxyPort(PROXY_PORT);
            botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS4);


            botsApi.registerBot(new AnonymizerBot(botOptions));




        } catch (TelegramApiRequestException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }

        ///-/--------------------

//    }


    }


}