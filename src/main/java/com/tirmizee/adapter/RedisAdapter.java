package com.tirmizee.adapter;

import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class RedisAdapter {

    private static int RETRY_PUSH = 0;
    private static int RETRY_GET = 0;


    public boolean push(String key, String value) {
        System.out.println(++RETRY_PUSH);
        throw new NullPointerException();
    }

    public String get(String key) {
        System.out.println(++RETRY_GET);
        throw new NullPointerException();
    }

}
