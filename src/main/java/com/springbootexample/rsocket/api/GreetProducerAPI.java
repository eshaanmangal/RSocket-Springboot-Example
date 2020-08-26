package com.springbootexample.rsocket.api;

/*
Single Response **/

import com.springbootexample.rsocket.model.GreetRequest;
import com.springbootexample.rsocket.model.GreetResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@Controller
public class GreetProducerAPI {

    @MessageMapping("greet")
    Mono<GreetResponse> greetResponseMono(GreetRequest greetRequest){
        return Mono.justOrEmpty(new GreetResponse("Hello" + greetRequest.getGreetRequest() + "->" + Instant.now()));
    }

    @MessageMapping("greet-stream")
    Flux<GreetResponse> greetStream(GreetRequest request) {
        return Flux.fromStream(Stream.generate(
                () -> new GreetResponse("Hello " + request.getGreetRequest() + " @ " + Instant.now())
        )).delayElements(Duration.ofSeconds(1));
    }
}
