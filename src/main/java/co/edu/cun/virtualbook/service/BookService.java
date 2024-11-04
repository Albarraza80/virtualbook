package co.edu.cun.virtualbook.service;

import co.edu.cun.virtualbook.domain.Author;
import co.edu.cun.virtualbook.domain.Book;
import co.edu.cun.virtualbook.repository.BookRepository;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BookService {
    private BookRepository bookRepository;

    public BookService() throws IOException {
        this.bookRepository = new BookRepository();
    }

    public Book createBook(
            String title,
            String genre,
            String editorial,
            Integer publicYear,
            Integer numPages,
            List<Author> authors // Acepta la lista de autores
    ) throws SQLException {
        Book book = new Book();

        book.setTitle(title);
        book.setGenre(genre);
        book.setPages(numPages);
        book.setPublicYear(publicYear);
        book.setEditorial(editorial);

        // Asocia los autores con el libro
        for (Author author : authors) {
            book.addAuthor(author);
        }

        return bookRepository.create(book);
    }

    public List<Book> findAll() throws SQLException {
        return bookRepository.getAll();
    }

    public Book update(
            Integer id,
            String title,
            String genre,
            String editorial,
            Integer publicYear,
            Integer numPages,
            List<Author> authors // Acepta la lista de autores para actualizar
    ) throws SQLException {
        Book book = bookRepository.findById(id);
        if (book != null) {
            book.setTitle(title);
            book.setGenre(genre);
            book.setPages(numPages);
            book.setPublicYear(publicYear);
            book.setEditorial(editorial);

            // Actualiza los autores
            book.getAuthorList().clear(); // Limpia la lista actual de autores
            for (Author author : authors) {
                book.addAuthor(author);
            }

            bookRepository.update(book); // Actualiza el libro en la base de datos
            System.out.println("Libro actualizado");
        }
        return book;
    }

    public void delete(Integer id) throws SQLException {
        Book book = bookRepository.findById(id);
        if (book != null) {
            bookRepository.delete(id);
            System.out.println("Libro borrado");
        } else {
            System.out.println("No se encontró el libro con ID: " + id);
        }
    }
}
