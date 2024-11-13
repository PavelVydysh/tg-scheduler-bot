package org.example.api.converter.poll;

import org.example.domain.model.poll.Poll;
import org.springframework.util.ObjectUtils;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public class PollConverter {

    public static Poll toPollWithoutAvailableAnswers(Message message) {
        if (ObjectUtils.isEmpty(message)) {
            return null;
        }

        Poll poll = new Poll();
        poll.setTitle(message.getText());
        poll.setTgChatId(message.getChatId());
        poll.setTgUserId(message.getFrom().getId());

        return poll;
    }

}
