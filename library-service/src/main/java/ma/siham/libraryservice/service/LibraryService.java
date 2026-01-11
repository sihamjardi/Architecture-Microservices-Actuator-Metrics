package ma.siham.libraryservice.service;

import jakarta.transaction.Transactional;
import ma.siham.libraryservice.domain.LibraryBook;
import ma.siham.libraryservice.repo.LibraryBookRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class LibraryService {

    private final LibraryBookRepository repo;
    private final BillingClient billing;

    public LibraryService(LibraryBookRepository repo, BillingClient billing) {
        this.repo = repo;
        this.billing = billing;
    }

    // Récupérer tous les livres
    public List<LibraryBook> findAll() {
        return repo.findAll();
    }

    //  Créer un nouveau livre
    public LibraryBook create(LibraryBook book) {
        // Vérifier si le titre existe déjà
        repo.findByTitle(book.getTitle()).ifPresent(b -> {
            throw new IllegalArgumentException("Title already exists");
        });
        return repo.save(book);
    }

    // Emprunter un livre
    @Transactional
    public BorrowResult borrow(long id) {
        LibraryBook book = repo.findByIdForUpdate(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        book.borrowOne();
        double price = billing.getPrice(id);

        return new BorrowResult(book.getId(), book.getTitle(), book.getStock(), price);
    }

    // Record pour retourner le résultat de borrow
    public record BorrowResult(Long id, String title, int stockLeft, double price) {}

}