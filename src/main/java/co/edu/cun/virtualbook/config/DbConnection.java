package co.edu.cun.virtualbook.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;

public class DbConnection{
    private Connection conenction;

    public DbConnection()
        throws IOException{
        ApplicationConfig config = ApplicationConfig.getInstance();
        String url = config.getUrl();
        String username = config.getUser();
        String password = config.getPassword();
        try{
            this.conenction = DriverManager.getConnection( url, username, password );
            System.out.println("Conexión exitosa");

        }catch( SQLException e ){
            System.out.println("Error de conexion con la base de datos " + e.getMessage());
        }
    }

    public Connection getConnection(){
        return this.conenction;
    }

    public void closeConnection()
        throws SQLException{
        if( this.conenction != null){
            this.conenction.close();
        }
    }
}
