package com.beaverly.springbootelklogging;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class Controller {

    @GetMapping("/echo")
    public String echoMessage() {
        log.info("Echo Triggered");
        return "Echo Triggered";
    }

    @PostMapping
    public Response sampleBody(@RequestBody Response response) {
        response.setName("UPDATED RESPONSE");

        return response;
    }

    @Data
    static class Response {
        private String name;
        private Integer age;
    }
}
