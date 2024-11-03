package co.edu.cun.virtualbook.service;

import co.edu.cun.virtualbook.domain.Book;
import co.edu.cun.virtualbook.repository.BookRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BookService{
    private BookRepository bookRepository;
    private Book book;

    public BookService()
        throws IOException{
        this.bookRepository = new BookRepository();
    }

    public Book createBook(
        String title,
        String genre,
        String editorial,
        Integer publicYear,
        Integer numPages
    )
        throws SQLException{
        //Book book = new Book();

        book.title();
        book.genre();
        book.pages();
        book.publicYear();
        book.editorial();

        return bookRepository.create( book );
    }

    public List<Book> findAll()
        throws SQLException{
        return bookRepository.getAll();
    }

    public Book update(
        Integer id,
        String title,
        String genre,
        String editorial,
        Integer publicYear,
        Integer numPages
    )
        throws SQLException{
        Book book = bookRepository.findById( id );
        if( book != null ){
            book.title();
            book.genre();
            book.pages();
            book.publicYear();
            book.editorial();
            bookRepository.update( book );
            System.out.println( "Libro actualizado" );
        }
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
