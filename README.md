# SpringBoot-Retry-Example

### Dependencies

```groovy

implementation 'org.springframework.retry:spring-retry'
implementation 'org.springframework.boot:spring-boot-starter-aop'

```

### Enable and Config

```java

@EnableRetry
@Configuration
public class RetryConfig {

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(200l);

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);

        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
        retryTemplate.setRetryPolicy(retryPolicy);

        return retryTemplate;
    }

}

```

### Controller

```java

@RestController
@AllArgsConstructor
public class RetryController {

    private final RetryService retryService;

    @GetMapping("/retry01")
    public String retry01(){
        retryService.retryWithRetryAble();
        return "retry01";
    }

    @GetMapping("/retry02")
    public String retry02(){
        retryService.retryWithRetryTemplate();
        return "retry02";
    }

}

```

### Service with @Retryable

```java

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

```

### Component with RetryTemplate

```java

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

```