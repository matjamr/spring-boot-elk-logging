package com.beaverly.springbootelklogging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class Controller {

    @GetMapping(    "/echo")
    public String echoMessage() {
        log.info("Echo Triggered");
        return "Echo Triggered";
    }

}
