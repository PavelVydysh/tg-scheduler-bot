package org.example.api.converter;

import org.example.domain.model.CustomPoll;
import org.example.infrastructure.configuration.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.objects.polls.input.InputPollOption;

import java.util.ArrayList;
import java.util.List;

@Component
public class PollConverter {

    @Value(Environment.COMMAND_POLL_IS_ANONYMOUS_KEY)
    private Boolean isAnonymousPoll;

    public SendPoll toSendPoll(CustomPoll customPoll) {
        List<InputPollOption> options = new ArrayList<>();
        customPoll.getAvailableAnswerTypes().forEach(answerType -> options.add(new InputPollOption(answerType.getTitle())));

        SendPoll sendPoll = SendPoll
                .builder()
                .chatId(customPoll.getChatId())
                .question(customPoll.getTitle())
                .options(options)
                .isAnonymous(isAnonymousPoll)
                .build();

        return sendPoll;
    }

}
