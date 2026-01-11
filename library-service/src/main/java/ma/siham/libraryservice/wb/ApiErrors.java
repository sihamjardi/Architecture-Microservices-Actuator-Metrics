package ma.siham.libraryservice.wb;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Gestion centralisée des erreurs REST.
 * Évite les erreurs 500 non contrôlées.
 */
@RestControllerAdvice
public class ApiErrors {

    /**
     * Erreurs métier (stock épuisé, etc.)
     */
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> conflict(IllegalStateException ex) {
        return Map.of("error", ex.getMessage());
    }

    /**
     * Erreurs d’arguments (id incorrect, doublon, etc.)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> badRequest(IllegalArgumentException ex) {
        return Map.of("error", ex.getMessage());
    }
}
