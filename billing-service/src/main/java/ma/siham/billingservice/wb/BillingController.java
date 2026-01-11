package ma.siham.billingservice.wb;

import org.springframework.web.bind.annotation.*;
import java.util.concurrent.ThreadLocalRandom;


/**
 * API REST du service de facturation.
 * Ce service est volontairement instable pour tester la résilience.
 */
@RestController
@RequestMapping("/api/billing")
public class BillingController {


    @GetMapping("/{bookId}")
    public double computePrice(@PathVariable long bookId,
                               @RequestParam(defaultValue = "false") boolean fail) {


        //  Permet de forcer une panne manuellement (?fail=true)
        if (fail) {
            throw new IllegalStateException("Billing service forced failure");
        }


        //  30% de panne aléatoire (simulation réseau / surcharge)
        if (ThreadLocalRandom.current().nextInt(100) < 30) {
            throw new IllegalStateException("Random billing failure");
        }


        //  Calcul simple du prix
        return 40.0 + (bookId % 5) * 10.0;
    }
}