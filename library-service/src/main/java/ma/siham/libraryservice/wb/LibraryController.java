package ma.siham.libraryservice.wb;

import ma.siham.libraryservice.domain.LibraryBook;
import ma.siham.libraryservice.service.LibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST principal du microservice Library.
 * Expose les endpoints HTTP utilisés par les clients.
 */
@RestController
@RequestMapping("/api/books")
public class LibraryController {

    private final LibraryService service;

    public LibraryController(LibraryService service) {
        this.service = service;
    }


    /**
     * Récupérer tous les livres
     */
    @GetMapping
    public List<LibraryBook> all() {
        return service.findAll();
    }

    /**
     * Créer un nouveau livre
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LibraryBook create(@RequestBody LibraryBook book) {
        return service.create(book);
    }

    /**
     * Emprunter un livre
     * - décrémente le stock
     * - appelle billing-service
     */
    @PostMapping("/{id}/borrow")
    public LibraryService.BorrowResult borrow(@PathVariable long id) {
        return service.borrow(id);
    }
}
