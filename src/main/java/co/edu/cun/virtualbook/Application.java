package co.edu.cun.virtualbook;

import co.edu.cun.virtualbook.form.BookForm;
import co.edu.cun.virtualbook.service.BookService;

import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JFrame;

public class Main{
    public static void main( String[] args )
        throws IOException, SQLException{
        BookForm book = new BookForm();
        book.setTitle("book");
        book.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        book.setVisible(true);
    }
}
