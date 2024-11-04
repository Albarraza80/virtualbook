package co.edu.cun.virtualbook.service;

import co.edu.cun.virtualbook.domain.Author;
import co.edu.cun.virtualbook.repository.AuthorRepository;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AuthorService {
    private AuthorRepository authorRepository;

    public AuthorService() throws IOException {
        this.authorRepository = new AuthorRepository();
    }

    public Author createAuthor(
            String name,
            String lastname,
            Integer birthYear,
            String nationality,
            String biography,
            String birthPlace
    ) throws SQLException {
        Author author = new Author(name, lastname, birthYear, nationality, biography, birthPlace);
        return authorRepository.create(author);
    }

    public List<Author> findAll() throws SQLException {
        return authorRepository.getAll();
    }

    public Author update(
            Integer id,
            String name,
            String lastname,
            Integer birthYear,
            String nationality,
            String biography,
            String birthPlace
    ) throws SQLException {

        Author author = authorRepository.findById(id);
        if (author != null) {
            author.setName(name);
            author.setLastname(lastname);
            author.setBirthYear(birthYear);
            author.setNationality(nationality);
            author.setBiography(biography);
            author.setBirthPlace(birthPlace);
            authorRepository.update(author);
            System.out.println("Autor actualizado");
        }
        return author;
    }

    public void delete(Integer id) throws SQLException {
        Author author = authorRepository.findById(id);
        if (author != null) {
            authorRepository.delete(id);
            System.out.println("Autor borrado");
        }
    }

    public Author findById(Integer id) throws SQLException {
        return authorRepository.findById(id);
    }
}
