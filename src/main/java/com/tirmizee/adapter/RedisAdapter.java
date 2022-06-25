package com.tirmizee.adapter;

import lombok.AllArgsConstructor;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RedisAdapter {

    private static int RETRY_PUSH = 0;
    private static int RETRY_GET = 0;

    private final RetryTemplate retryTemplate;

    public boolean push(String key, String value) {
        return retryTemplate.execute(context -> {
            System.out.println("RETRY_PUSH " + (++RETRY_PUSH));
            if (true) throw new NullPointerException();
            return true;
        });
    }

    public String get(String key) {
        return retryTemplate.execute(context -> {
            System.out.println(("RETRY_GET " + ++RETRY_GET));
            if (true) throw new NullPointerException();
            return "value";
        });
    }

}
