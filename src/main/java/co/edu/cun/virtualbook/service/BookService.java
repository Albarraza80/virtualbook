package co.edu.cun.virtualbook.service;

import co.edu.cun.virtualbook.domain.Book;
import co.edu.cun.virtualbook.repository.BookRepository;

import java.io.IOException;
import java.sql.SQLException;

public class BookService{
    private BookRepository bookRepository;

    public BookService()
        throws IOException{
        this.bookRepository = new BookRepository();
    }

    public Book createBook( String title, String genre)
        throws SQLException{
        Book book = new Book();
        book.setTitle( title );
        book.setGenre( genre );

        return bookRepository.create( book );

    }

}
