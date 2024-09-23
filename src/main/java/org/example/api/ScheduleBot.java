package org.example.api;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.api.command.CommandRouter;
import org.example.infrastructure.configuration.BotProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Slf4j
@Component
public class ScheduleBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    private final BotProperties botProperties;

    @Getter
    private final TelegramClient telegramClient;
    private final CommandRouter commandRouter;

    @Autowired
    public ScheduleBot(BotProperties botProperties, @Lazy CommandRouter commandRouter) {
        this.botProperties = botProperties;
        this.telegramClient = new OkHttpTelegramClient(getBotToken());
        this.commandRouter = commandRouter;
    }

    @Override
    public String getBotToken() {
        return botProperties.getToken();
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        if(update.hasMessage()) {
            log.info(update.getMessage().getText());
            commandRouter.handle(update);
        }
    }
}
