package com.bhargav.hystrix.consumer.controller;

import com.bhargav.hystrix.consumer.service.GreetingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/greet")
public class GreetingController {

    @Autowired
    private GreetingService greetingService;

    @GetMapping("/{username}")
    public String getGreetingMessage(@PathVariable("username") String userName) {
        try {
            log.warn("Main Thread: {} ({})",
                    Thread.currentThread().getName(),
                    Thread.currentThread().getId());
            return greetingService.getMessage(userName);
        } finally {
            log.warn("Main Thread: {} ({})",
                    Thread.currentThread().getName(),
                    Thread.currentThread().getId());
        }
    }

}
