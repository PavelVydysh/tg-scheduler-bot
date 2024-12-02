package org.example.infrastructure.feign.dto.poll;

import org.example.infrastructure.feign.dto.PollRequestClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "poll-service",
        url = "${feign.client.config.poll-service.url}",
        path = "${feign.client.config.poll-service.api-base-url}"
)
public interface PollServiceClient {

    @PostMapping
    void createPoll(@RequestBody PollRequestClientDto pollDto);

}
