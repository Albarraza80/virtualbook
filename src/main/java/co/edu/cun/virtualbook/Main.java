package co.edu.cun.virtualbook;

import co.edu.cun.virtualbook.domain.Book;
import co.edu.cun.virtualbook.service.BookService;

import java.io.IOException;
import java.sql.SQLException;

public class Main{
    public static void main( String[] args )
        throws IOException, SQLException{
        BookService bookService = new BookService();
        bookService.delete(  1 );
    }
}
