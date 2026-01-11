package ma.siham.billingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Point d’entrée du microservice Billing.
 * Chaque microservice Spring Boot possède sa propre JVM.
 */
@SpringBootApplication
public class BillingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }
}