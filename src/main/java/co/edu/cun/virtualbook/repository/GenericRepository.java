package co.edu.cun.virtualbook.repository;

import java.sql.SQLException;
import java.util.List;

public interface GenericRepository <T, ID>{

    T create( T entity )
        throws SQLException;
    List<T> getAll()
        throws SQLException;

    T update( T entity )
        throws SQLException;

    void delete( ID id )
        throws SQLException;
}
