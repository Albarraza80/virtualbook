package co.edu.cun.virtualbook.domain;

import java.util.ArrayList;
import java.util.List;

public class Author {
    private Integer id;
    private String name;
    private String lastname;
    private Integer birthYear;
    private String nationality;
    private String biography;
    private String birthPlace;
    private List<Book> books; // Relación muchos a muchos con Book

    public Author(String name, String lastname, Integer birthYear, String nationality, String biography, String birthPlace) {
        this.name = name;
        this.lastname = lastname;
        this.birthYear = birthYear;
        this.nationality = nationality;
        this.biography = biography;
        this.birthPlace = birthPlace;
        this.books = new ArrayList<>();
    }

    public Author(Integer id, String name, String lastname, Integer birthYear, String nationality, String biography, String birthPlace) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.birthYear = birthYear;
        this.nationality = nationality;
        this.biography = biography;
        this.birthPlace = birthPlace;
        this.books = new ArrayList<>();
    }

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    // Método para añadir un libro a la lista
    public void addBook(Book book) {
        if (!books.contains(book)) {
            books.add(book);
            book.getAuthorList().add(this); // Añade el autor al libro también
        }
    }
}
