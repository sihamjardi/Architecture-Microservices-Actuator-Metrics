package ma.siham.libraryservice.wb;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint de debug pour prouver le multi-instances.
 */
@RestController
public class InstanceController {

    @Value("${server.port}")
    private int port;

    @GetMapping("/api/debug/instance")
    public String instance() {
        // HOSTNAME est injecté automatiquement par Docker
        String host = System.getenv().getOrDefault("HOSTNAME", "local");
        return "instance=" + host + " internalPort=" + port;
    }
}
