package org.example.api.converter.poll;

import org.example.domain.model.poll.Poll;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public class PollConverter {

    private final static Integer DEFAULT_VERSION = 1;

    public static Poll toPollWithoutAvailableAnswers(Message message) {
        Poll poll = new Poll();
        poll.setTgChatId(message.getChatId());
        poll.setTitle(message.getText());
        poll.setVersion(DEFAULT_VERSION);

        return poll;
    }

}
