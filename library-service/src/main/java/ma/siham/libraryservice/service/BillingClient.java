package ma.siham.libraryservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class BillingClient {


    private final RestTemplate rest;
    private final String baseUrl;


    public BillingClient(RestTemplate rest,
                         @Value("${billing.base-url}") String baseUrl) {
        this.rest = rest;
        this.baseUrl = baseUrl;
    }

    @Retry(name = "billing")
    @CircuitBreaker(name = "billing", fallbackMethod = "fallback")
    public double getPrice(long bookId) {
        return rest.getForObject(baseUrl + "/api/billing/" + bookId, Double.class);
    }


    // 🛟 Fallback obligatoire (même signature + Throwable)
    public double fallback(long bookId, Throwable ex) {
        return 0.0;
    }
}