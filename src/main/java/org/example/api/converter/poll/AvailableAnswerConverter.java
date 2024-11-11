package org.example.api.converter.poll;

import org.example.domain.model.poll.AvailableAnswer;
import org.example.domain.model.poll.Poll;
import org.springframework.util.ObjectUtils;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public class AvailableAnswerConverter {

    private static final Integer DEFAULT_POSITION = 0;

    public static AvailableAnswer toAvailableAnswer(Poll poll, Message message) {
        if(ObjectUtils.isEmpty(poll) || ObjectUtils.isEmpty(message)) {
            return null;
        }

        Integer currentPosition = DEFAULT_POSITION;

        for(AvailableAnswer availableAnswer : poll.getAvailableAnswers()) {
            if(availableAnswer.getPosition() > currentPosition) {
                currentPosition = availableAnswer.getPosition();
            }
        }

        AvailableAnswer availableAnswer = new AvailableAnswer();
        availableAnswer.setPosition(currentPosition + 1);
        availableAnswer.setTitle(message.getText());

        return availableAnswer;
    }

}
