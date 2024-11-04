package co.edu.cun.virtualbook.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;

public class DbConnection{
    private static DbConnection instance;
    private Connection connection;

    private DbConnection() throws IOException {
        ApplicationConfig config = ApplicationConfig.getInstance();
        String url = config.getUrl();
        String username = config.getUser();
        String password = config.getPassword();
        try {
            this.connection = DriverManager.getConnection(url, username, password);
            System.out.println("Conexión exitosa");
        } catch (SQLException e) {
            System.out.println("Error de conexión con la base de datos: " + e.getMessage());
        }
    }

    public static DbConnection getInstance() throws IOException {
        if (instance == null) {
            synchronized (DbConnection.class) { // Añadimos sincronización para seguridad en entornos multi-hilo
                if (instance == null) {
                    instance = new DbConnection();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void closeConnection() throws SQLException {
        if (this.connection != null) {
            this.connection.close();
        }
    }
}
