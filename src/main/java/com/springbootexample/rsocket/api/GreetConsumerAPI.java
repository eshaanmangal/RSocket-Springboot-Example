package com.springbootexample.rsocket.api;

import com.springbootexample.rsocket.model.GreetRequest;
import com.springbootexample.rsocket.model.GreetResponse;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class GreetConsumerAPI {
    private final RSocketRequester requester;

    @GetMapping("/greet/{name}")
    public Publisher<GreetResponse> greetResponsePublisher(@PathVariable String name){
        return requester
                .route("greet")
                .data(new GreetRequest(name))
                .retrieveMono(GreetResponse.class);
    }

    @GetMapping(value = "/greet-stream/{name}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Publisher<GreetResponse> greetStream(@PathVariable String name) {
        return requester
                .route("greet-stream")
                .data(new GreetRequest(name))
                .retrieveFlux(GreetResponse.class);
    }
}
