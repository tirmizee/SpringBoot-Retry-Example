package com.tirmizee.service;

import com.tirmizee.adapter.RedisAdapter;
import com.tirmizee.adapter.WebClientAdapter;
import lombok.AllArgsConstructor;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RetryService {

    private static int RETRY = 0;
    private final RedisAdapter redisAdapter;
    private final WebClientAdapter webClientAdapter;

    @Retryable(value = NullPointerException.class, maxAttempts = 2)
    public void retryWithRetryAble() {
        System.out.println(++RETRY);
        throw new NullPointerException();
    }

}
