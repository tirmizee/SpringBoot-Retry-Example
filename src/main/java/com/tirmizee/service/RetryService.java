package com.tirmizee.service;

import com.tirmizee.adapter.RedisAdapter;
import lombok.AllArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RetryService {

    private static int RETRY = 0;
    private final RedisAdapter redisAdapter;

    @Retryable(value = NullPointerException.class, maxAttempts = 2,  backoff = @Backoff(delay = 100))
    public void retryWithRetryAble() {
        System.out.println(++RETRY);
        throw new NullPointerException();
    }

    public void retryWithRetryTemplate() {
        try { redisAdapter.get("xxx"); } catch (Exception e) { System.out.println(e.getClass().getSimpleName()); }
        try { redisAdapter.push("xxx", "xxx"); } catch (Exception e) { System.out.println(e.getClass().getSimpleName()); }
    }

}
