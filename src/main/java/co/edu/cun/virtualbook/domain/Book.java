package co.edu.cun.virtualbook.domain;

public class Book{
    private int id;
    private String title;
    private String genre;

    public Book( int id, String title, String genre ){
        this.id = id;
        this.title = title;
        this.genre = genre;
        //Agregar las propiedades restantes al libro
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
}
