package co.edu.cun.virtualbook.domain;

import java.util.List;

public class Book{
    private int id;
    private String title;
    private String genre;
    private Integer pages;
    private int publicYear;
    private String editorial;
    private List<Author> authorList;

    public Book( int id, String title, String genre ){
        this.id = id;
        this.title = title;
        this.genre = genre;
        //TODO:Agregar las propiedades restantes al libro
    }

    public Book(){
    }

    public int getId(){
        return id;
    }

    public void setId( int id ){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle( String title ){
        this.title = title;
    }

    public String getGenre(){
        return genre;
    }

    public void setGenre( String genre ){
        this.genre = genre;
    }

    public Integer getPages(){
        return pages;
    }

    public void setPages( Integer pages ){
        this.pages = pages;
    }

    public int getPublicYear(){
        return publicYear;
    }

    public void setPublicYear( int publicYear ){
        this.publicYear = publicYear;
    }

    public List<Author> getAuthorList(){
        return authorList;
    }

    public void setAuthorList( List<Author> authorList ){
        this.authorList = authorList;
    }

    public String getEditorial(){
        return editorial;
    }

    public void setEditorial( String editorial ){
        this.editorial = editorial;
    }
}
