package org.example.api.controller.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api.Bot;
import org.example.api.converter.poll.PollConverter;
import org.example.api.dto.poll.PollCreateRequestDto;
import org.example.domain.model.poll.Poll;
import org.example.domain.service.PollService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "${spring.application.public-prefix-path}/polls")
public class PollsController {

    private final Bot bot;
    private final PollService pollService;

    @PostMapping
    public void createPoll(@RequestBody @Valid PollCreateRequestDto pollDto) {
        Poll poll = PollConverter.toPoll(pollDto);
        pollService.savePoll(poll);
    }

}
