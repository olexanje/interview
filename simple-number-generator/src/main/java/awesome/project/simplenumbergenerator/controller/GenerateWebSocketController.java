package awesome.project.simplenumbergenerator.controller;


import awesome.project.simplenumbergenerator.entity.Generated;
import awesome.project.simplenumbergenerator.entity.GeneratedColumn;
import awesome.project.simplenumbergenerator.entity.NumbersCount;
import awesome.project.simplenumbergenerator.service.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GenerateWebSocketController {

    @Autowired
    private GeneratorService generator;

    @MessageMapping("/request-to-generate")
    @SendTo("/topic/response-with-generated")
    public Generated greeting(NumbersCount numbersCount) {
        return generator.getSimpleNumbersArrays(numbersCount);
    }

}

