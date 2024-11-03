package co.edu.cun.virtualbook.service;

import co.edu.cun.virtualbook.domain.Book;
import co.edu.cun.virtualbook.repository.BookRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BookService{
    private BookRepository bookRepository;

    public BookService()
        throws IOException{
        this.bookRepository = new BookRepository();
    }

    //TODO: recibir las demas propiedades
    public Book createBook( String title, String genre )
        throws SQLException{
        Book book = new Book();
        book.setTitle( title );
        book.setGenre( genre );
        book.setPages( 500 );
        book.setPublicYear( 1950 );
        book.setEditorial( "Norma" );


        return bookRepository.create( book );

    }

    public List<Book> findAll()
        throws SQLException{
        return bookRepository.getAll();
    }

    //TODO: recibir las demas propiedades
    public Book update( String title, String genre, Integer id )
        throws SQLException{
        Book book = bookRepository.findById( id );
        if( book != null ){
            //TODO: aqui tambien
            book.setTitle( title );
            book.setGenre( genre );
            book.setPages( 250 );
            book.setPublicYear( 1952 );
            book.setEditorial( "Planeta" );
            bookRepository.update( book );
        }
        System.out.println("Libro actualizado");
        return book;
    }

    public void delete( Integer id )
        throws SQLException{
        Book book = bookRepository.findById( id );
        if( book != null ){
            bookRepository.delete( id );
        }
        System.out.println( "Libro Borrado" );
    }
}
