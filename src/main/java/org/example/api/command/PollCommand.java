package org.example.api.command;

import lombok.extern.slf4j.Slf4j;
import org.example.api.ScheduleBot;
import org.example.api.converter.PollConverter;
import org.example.api.mapper.PollMapper;
import org.example.domain.model.CustomPoll;
import org.example.domain.service.PollService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class PollCommand extends Command{

    private final PollService pollService;
    private final PollConverter pollConverter;

    public PollCommand(ScheduleBot bot, PollService pollService, PollConverter pollConverter) {
        super(bot);
        this.pollService = pollService;
        this.pollConverter = pollConverter;
    }

    @Override
    void handle(Update update) throws TelegramApiException {
        CustomPoll customPoll = pollService.preparePoll(update.getMessage().getChatId());
        SendPoll sendPoll = pollConverter.toSendPoll(customPoll);
        Message response = bot.getTelegramClient().execute(sendPoll);

        if(response.hasPoll()) {
            Poll responsePoll = response.getPoll();
            PollMapper.toCustomPoll(customPoll, responsePoll);
            pollService.savePoll(customPoll);
        }
    }

}
