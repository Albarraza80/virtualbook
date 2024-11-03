}
    }
        return null;

        }
            return book;

            book.setEditorial( result.getString( "editorial" ) );
            book.setPublicYear( result.getInt( "public_year" ) );
            book.setPages( result.getInt( "pages" ) );
            book.setGenre( result.getString( "genre" ) );
            book.setTitle( result.getString( "title" ) );
            book.setId( result.getInt( "id" ) );
            Book book = new Book();

        if( result.next() ) {

        ResultSet result = stmt.executeQuery();
        stmt.setInt( 1, id );
        PreparedStatement stmt = connection.prepareStatement( query );
        String query = "SELECT * FROM book WHERE id = ?";
        throws SQLException {
    public Book findById( Integer id )

    }
        stmt.executeUpdate();
        stmt.setInt( 1, id );
        PreparedStatement stmt = connection.prepareStatement( query );
        String query = "DELETE FROM book WHERE id = ?";
        throws SQLException {
    public void delete( Integer id )
    @Override

    }
        return book;

        stmt.executeUpdate();
        stmt.setInt( 6, book.getId() );
        stmt.setString( 5, book.getEditorial() );
        stmt.setInt( 4, book.getPublicYear());
        stmt.setInt( 3, book.getPages() );
        stmt.setString( 2, book.getGenre() );
        stmt.setString( 1, book.getTitle() );

        PreparedStatement stmt = connection.prepareStatement( query );
        String query = "UPDATE book SET title = ?, genre = ?, pages = ?, publicYear = ?, editorial = ? WHERE id = ?";
    public Book update( Book book ) throws SQLException {
    @Override

    }
        return books;

        }
            books.add( book );
            book.setEditorial( result.getString( "editorial" ) );
            book.setPublicYear( result.getInt( "public_year" ) );
            book.setPages( result.getInt( "pages" ) );
            book.setGenre( result.getString( "genre" ) );
            book.setTitle( result.getString( "title" ) );
            book.setId( result.getInt( "id" ) );
            Book book = new Book();
        while( result.next() ){

        List<Book> books = new ArrayList<>();
        ResultSet result = stmt.executeQuery( query );

        Statement stmt = connection.createStatement();
        String query = "SELECT * FROM book";
    public List<Book> getAll() throws SQLException {
    @Override

    }
        return book;

        }
            book.setId( result.getInt( 1 ) );
        if( result.next() ){

        ResultSet result = stmt.getGeneratedKeys();
        stmt.executeUpdate();

        stmt.setString( 5, book.getEditorial() );
        stmt.setInt( 4, book.getPublicYear() );
        stmt.setInt( 3, book.getPages() );
        stmt.setString( 2, book.getGenre() );
        stmt.setString( 1, book.getTitle() );

        PreparedStatement stmt = connection.prepareStatement( query, PreparedStatement.RETURN_GENERATED_KEYS );
        String query = "INSERT INTO book (title, genre, pages, public_year, editorial ) VALUES (?, ?, ?, ?, ?)";
    public Book create( Book book ) throws SQLException {
    @Override

    }
        this.connection = dbConnection.getConnection();
        DbConnection dbConnection = new DbConnection();
        throws IOException{
    public BookRepository()

    protected Connection connection;

public class BookRepository implements GenericRepository<Book, Integer>{

import java.util.List;
import java.util.ArrayList;
import java.sql.*;
import java.io.IOException;

import co.edu.cun.virtualbook.domain.Book;
import co.edu.cun.virtualbook.config.DbConnection;

package co.edu.cun.virtualbook.repository;
