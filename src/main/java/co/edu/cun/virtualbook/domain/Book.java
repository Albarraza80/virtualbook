package co.edu.cun.virtualbook.domain;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private int id;
    private String title;
    private String genre;
    private Integer pages;
    private int publicYear;
    private String editorial;
    private List<Author> authorList; // Relación muchos a muchos con Author

    public Book() {
        this.authorList = new ArrayList<>();
    }

    public Book(int id, String title, String genre, Integer pages, int publicYear, String editorial) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.pages = pages;
        this.publicYear = publicYear;
        this.editorial = editorial;
        this.authorList = new ArrayList<>();
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public int getPublicYear() {
        return publicYear;
    }

    public void setPublicYear(int publicYear) {
        this.publicYear = publicYear;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }

    // Método para añadir un autor a la lista
    public void addAuthor(Author author) {
        if (!authorList.contains(author)) {
            authorList.add(author);
            author.getBooks().add(this); // Añade el libro a la lista de libros del autor también
        }
    }
}
