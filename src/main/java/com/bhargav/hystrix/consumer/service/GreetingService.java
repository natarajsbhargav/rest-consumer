package com.bhargav.hystrix.consumer.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
public class GreetingService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallback_getMessage",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
            })
    public String getMessage(String userName) {
        log.warn("Hystrix Thread: {} ({})",
                Thread.currentThread().getName(),
                Thread.currentThread().getId());
        String greetMessage = restTemplate.exchange("http://localhost:9191/api/users/{username}",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<String>() {}, userName).getBody();
        log.warn("Hystrix Thread: {} ({}), Msg: {}",
                Thread.currentThread().getName(),
                Thread.currentThread().getId(), greetMessage);
        return greetMessage;
    }

    public String fallback_getMessage(String userName) {
        log.warn("Hystrix Fallback Thread: {} ({})",
                Thread.currentThread().getName(),
                Thread.currentThread().getId());
        return "Fallback Message " + userName + " !";
    }

}
