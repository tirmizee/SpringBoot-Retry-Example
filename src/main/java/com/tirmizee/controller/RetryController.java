package com.tirmizee.controller;

import com.tirmizee.service.RetryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RetryController {

    private final RetryService retryService;

    @GetMapping("/retry01")
    public String retry01(){
        retryService.retryWithRetryAble();
        return "retry01";
    }

}
