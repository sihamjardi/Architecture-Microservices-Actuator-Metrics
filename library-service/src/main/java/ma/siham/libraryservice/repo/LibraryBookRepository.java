package ma.siham.libraryservice.repo;

import jakarta.persistence.LockModeType;
import ma.siham.libraryservice.domain.LibraryBook;
import org.springframework.data.jpa.repository.*;


import java.util.Optional;


/**
 * Repository JPA avec verrou pessimiste.
 * Indispensable en environnement multi-instances.
 */
public interface LibraryBookRepository extends JpaRepository<LibraryBook, Long> {

    // Trouver un livre par titre
    Optional<LibraryBook> findByTitle(String title);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b from LibraryBook b where b.id = :id")
    Optional<LibraryBook> findByIdForUpdate(Long id);
}