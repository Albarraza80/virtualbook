package co.edu.cun.virtualbook.repository;

import co.edu.cun.virtualbook.config.DbConnection;
import co.edu.cun.virtualbook.domain.Author;
import co.edu.cun.virtualbook.domain.Book;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookRepository implements GenericRepository<Book, Integer> {

    private Connection connection;

    public BookRepository() throws IOException {
        this.connection = DbConnection.getInstance().getConnection();
    }

    @Override
    public Book create(Book book) throws SQLException {
        String query = "INSERT INTO book (title, genre, pages, public_year, editorial) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

        stmt.setString(1, book.getTitle());
        stmt.setString(2, book.getGenre());
        stmt.setInt(3, book.getPages());
        stmt.setInt(4, book.getPublicYear());
        stmt.setString(5, book.getEditorial());

        stmt.executeUpdate();
        ResultSet result = stmt.getGeneratedKeys();

        if (result.next()) {
            book.setId(result.getInt(1));
        }

        // Insertar relación entre libro y autores
        addAuthorsToBook(book);

        return book;
    }

    @Override
    public List<Book> getAll() throws SQLException {
        String query = "SELECT b.*, a.id AS author_id, a.name AS author_name, a.lastname AS author_lastname " +
                "FROM book b " +
                "LEFT JOIN book_author ba ON b.id = ba.book_id " +
                "LEFT JOIN author a ON ba.author_id = a.id";

        Statement stmt = connection.createStatement();
        ResultSet result = stmt.executeQuery(query);

        List<Book> books = new ArrayList<>();
        // Usar un mapa para evitar duplicados
        Map<Integer, Book> bookMap = new HashMap<>();

        while (result.next()) {
            int bookId = result.getInt("id");

            Book book = bookMap.computeIfAbsent(bookId, id -> {
                try {
                    return new Book(
                            id,
                            result.getString("title"),
                            result.getString("genre"),
                            result.getInt("pages"),
                            result.getInt("public_year"),
                            result.getString("editorial")
                    );
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            // Obtener los datos del autor
            int authorId = result.getInt("author_id");
            if (authorId > 0) { // Verificamos si hay un autor
                Author author = new Author(
                        result.getInt("author_id"),
                        result.getString("author_name"),
                        result.getString("author_lastname"),
                        null,
                        null,
                        null,
                        null
                );
                book.addAuthor(author);
            }
        }

        books.addAll(bookMap.values());
        return books;
    }

    @Override
    public Book update(Book book) throws SQLException {
        String query = "UPDATE book SET title = ?, genre = ?, pages = ?, public_year = ?, editorial = ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);

        stmt.setString(1, book.getTitle());
        stmt.setString(2, book.getGenre());
        stmt.setInt(3, book.getPages());
        stmt.setInt(4, book.getPublicYear());
        stmt.setString(5, book.getEditorial());
        stmt.setInt(6, book.getId());
        stmt.executeUpdate();

        // Actualiza las relaciones de autores
        updateAuthorsInBook(book);

        return book;
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String query = "DELETE FROM book WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    public Book findById(Integer id) throws SQLException {
        String query = "SELECT * FROM book WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet result = stmt.executeQuery();

        if (result.next()) {
            return new Book(
                    result.getInt("id"),
                    result.getString("title"),
                    result.getString("genre"),
                    result.getInt("pages"),
                    result.getInt("public_year"),
                    result.getString("editorial")
            );
        }
        return null;
    }

    // Método para añadir autores a la tabla de relación book_author
    public void addAuthorsToBook(Book book) throws SQLException {
        String query = "INSERT INTO book_author (book_id, author_id) VALUES (?, ?)";
        PreparedStatement stmt = connection.prepareStatement(query);

        for (Author author : book.getAuthorList()) {
            stmt.setInt(1, book.getId());
            stmt.setInt(2, author.getId()); // Asume que el autor tiene un ID
            stmt.addBatch(); // Añade al lote
        }

        stmt.executeBatch(); // Ejecuta el lote de inserciones
    }

    // Método para actualizar la relación de autores
    public void updateAuthorsInBook(Book book) throws SQLException {
        // Primero eliminamos las relaciones existentes
        String deleteQuery = "DELETE FROM book_author WHERE book_id = ?";
        PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery);
        deleteStmt.setInt(1, book.getId());
        deleteStmt.executeUpdate();

        // Luego insertamos los nuevos autores
        addAuthorsToBook(book);
    }
}
