package co.edu.cun.virtualbook.repository;

import co.edu.cun.virtualbook.config.DbConnection;
import co.edu.cun.virtualbook.domain.Category;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    private Connection connection;

    public CategoryRepository() throws IOException {
        this.connection = DbConnection.getInstance().getConnection();
    }

    public List<Category> findAll() {
        String query = "SELECT * FROM category";
        List<Category> categories = new ArrayList<>();

        try (Statement stmt = this.connection.createStatement();
             ResultSet result = stmt.executeQuery(query)) {

            while (result.next()) {
                Category category = new Category(
                        result.getInt("id"),
                        result.getString("name")
                );
                categories.add(category);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar categorías: " + e.getMessage());
        }

        return categories;
    }
}
