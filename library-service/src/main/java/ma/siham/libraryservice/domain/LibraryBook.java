package ma.siham.libraryservice.domain;

import jakarta.persistence.*;

/**
 * Entité représentant un livre en base de données.
 */
@Entity
@Table(name = "library_book")
public class LibraryBook {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String title;


    @Column(nullable = false)
    private String author;


    @Column(nullable = false)
    private int stock;


    public void borrowOne() {
        // Règle métier centralisée
        if (stock <= 0) {
            throw new IllegalStateException("No copies left");
        }
        stock--;
    }

    public LibraryBook() {
    }

    // getters / setters omis pour lisibilité


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}