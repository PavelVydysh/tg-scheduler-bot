package org.example.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.model.AnswerType;
import org.example.domain.model.CustomPoll;
import org.example.domain.repository.AnswerTypeRepository;
import org.example.domain.repository.PollRepository;
import org.example.domain.repository.VersionRepository;
import org.example.infrastructure.configuration.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PollService {

    private final VersionRepository versionRepository;

    private final AnswerTypeRepository answerTypeRepository;

    private final PollRepository pollRepository;

    private final String dateFormat = "dd MMMM yyyy";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
    @Value(Environment.COMMAND_POLL_TITLE)
    private String title = "Кто будет сегодня играть?";
    private final String titlePrefixFormat = "%s\n";

    public CustomPoll preparePoll(Long chatId) {
        Integer lastVersion = versionRepository.findLastVersion();
        List<AnswerType> answerTypes =  answerTypeRepository.findAllByVersion(lastVersion);
        Collections.sort(answerTypes);

        CustomPoll customPoll = new CustomPoll();
        customPoll.setChatId(chatId);
        customPoll.setVersion(lastVersion);
        customPoll.setTitle(String.format(titlePrefixFormat + title, formatter.format(LocalDate.now())));
        customPoll.setCreatedDate(LocalDate.now());
        customPoll.setAvailableAnswerTypes(answerTypes);

        return customPoll;
    }

    public CustomPoll savePoll(CustomPoll customPoll) {
        return pollRepository.create(customPoll);
    }

}
