package co.edu.cun.virtualbook.repository;

import co.edu.cun.virtualbook.config.DbConnection;
import co.edu.cun.virtualbook.domain.Author;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorRepository implements GenericRepository<Author, Integer> {

    private Connection connection;

    public AuthorRepository() throws IOException {
        this.connection = DbConnection.getInstance().getConnection();
    }

    @Override
    public Author create(Author author) throws SQLException {
        String query = "INSERT INTO author (name, lastname, birth_year, nationality, biography, birth_place) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

        setAuthorProperties(author, stmt);

        stmt.executeUpdate();
        ResultSet result = stmt.getGeneratedKeys();

        if (result.next()) {
            author.setId(result.getInt(1));
        }

        return author;
    }

    @Override
    public List<Author> getAll() throws SQLException {
        String query = "SELECT * FROM author";
        Statement stmt = connection.createStatement();

        ResultSet result = stmt.executeQuery(query);
        List<Author> authors = new ArrayList<>();

        while (result.next()) {
            authors.add(new Author(
                    result.getInt("id"),
                    result.getString("name"),
                    result.getString("lastname"),
                    result.getInt("birth_year"),
                    result.getString("nationality"),
                    result.getString("biography"),
                    result.getString("birth_place")
            ));
        }

        return authors;
    }

    @Override
    public Author update(Author author) throws SQLException {
        String query = "UPDATE author SET name = ?, lastname = ?, birth_year = ?, nationality = ?, biography = ?, birth_place = ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);

        setAuthorProperties(author, stmt);
        stmt.setInt(7, author.getId());
        stmt.executeUpdate();

        return author;
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String query = "DELETE FROM author WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    public Author findById(Integer id) throws SQLException {
        String query = "SELECT * FROM author WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet result = stmt.executeQuery();

        if (result.next()) {
            return new Author(
                    result.getInt("id"),
                    result.getString("name"),
                    result.getString("lastname"),
                    result.getInt("birth_year"),
                    result.getString("nationality"),
                    result.getString("biography"),
                    result.getString("birth_place")
            );
        }
        return null;
    }

    private void setAuthorProperties(Author author, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, author.getName());
        stmt.setString(2, author.getLastname());
        stmt.setInt(3, author.getBirthYear());
        stmt.setString(4, author.getNationality());
        stmt.setString(5, author.getBiography());
        stmt.setString(6, author.getBirthPlace());
    }
}
