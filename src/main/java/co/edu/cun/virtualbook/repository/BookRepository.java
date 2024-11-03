package co.edu.cun.virtualbook.repository;

import co.edu.cun.virtualbook.config.DbConnection;
import co.edu.cun.virtualbook.domain.Book;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository implements GenericRepository<Book, Integer>{

    protected Connection connection;

    public BookRepository()
        throws IOException{
        DbConnection dbConnection = new DbConnection();
        this.connection = dbConnection.getConnection();
    }

    @Override
    public Book create( Book book ) throws SQLException {
        String query = "INSERT INTO book (title, genre, pages, public_year, editorial ) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement( query, PreparedStatement.RETURN_GENERATED_KEYS );
        
        stmt.setString( 1, book.getTitle() );
        stmt.setString( 2, book.getGenre() );
        stmt.setInt( 3, book.getPages() );
        stmt.setInt( 4, book.getPublicYear() );
        stmt.setString( 5, book.getEditorial() );
        
        stmt.executeUpdate();
        ResultSet result = stmt.getGeneratedKeys();
        
        if( result.next() ){
            book.setId( result.getInt( 1 ) );
        }
        
        return book;
    }

    @Override
    public List<Book> getAll() throws SQLException {
        String query = "SELECT * FROM book";
        Statement stmt = connection.createStatement();
        
        ResultSet result = stmt.executeQuery( query );
        List<Book> books = new ArrayList<>();
        
        while( result.next() ){
            Book book = new Book();
            book.setId( result.getInt( "id" ) );
            book.setTitle( result.getString( "title" ) );
            book.setGenre( result.getString( "genre" ) );
            book.setPages( result.getInt( "pages" ) );
            book.setPublicYear( result.getInt( "public_year" ) );
            book.setEditorial( result.getString( "editorial" ) );
            books.add( book );
        }
        
        return books;
    }

    @Override
    public Book update( Book book ) throws SQLException {
        String query = "UPDATE book SET title = ?, genre = ?, pages = ?, publicYear = ?, editorial = ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement( query );
        
        stmt.setString( 1, book.getTitle() );
        stmt.setString( 2, book.getGenre() );
        stmt.setInt( 3, book.getPages() );
        stmt.setInt( 4, book.getPublicYear());
        stmt.setString( 5, book.getEditorial() );
        stmt.setInt( 6, book.getId() );
        stmt.executeUpdate();

        return book;
    }

    @Override
    public void delete( Integer id )
        throws SQLException {
        String query = "DELETE FROM book WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement( query );
        stmt.setInt( 1, id );
        stmt.executeUpdate();
    }

    public Book findById( Integer id )
        throws SQLException {
        String query = "SELECT * FROM book WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement( query );
        stmt.setInt( 1, id );
        ResultSet result = stmt.executeQuery();
            
        if( result.next() ) {
            
            Book book = new Book();
            book.setId( result.getInt( "id" ) );
            book.setTitle( result.getString( "title" ) );
            book.setGenre( result.getString( "genre" ) );
            book.setPages( result.getInt( "pages" ) );
            book.setPublicYear( result.getInt( "public_year" ) );
            book.setEditorial( result.getString( "editorial" ) );
            
            return book;
        }
            
        return null;
    }
}
