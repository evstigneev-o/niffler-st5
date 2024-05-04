package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.DataBase;
import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.jdbc.DataSourceProvider;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SpendRepositoryJdbc implements SpendRepository {

    private static final DataSource spendDataSource = DataSourceProvider.dataSource(DataBase.SPEND);

    @Override
    public CategoryEntity createCategory(CategoryEntity category) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO category(category,username) VALUES (?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS
             )) {
            statement.setString(1, category.getCategory());
            statement.setString(2, category.getUsername());
            statement.executeUpdate();

            UUID generatedId;
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if(resultSet.next()){
                generatedId = UUID.fromString(resultSet.getString("id"));
                } else throw new IllegalStateException("Can't access to id");
            }
            category.setId(generatedId);
            return category;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeCategory(CategoryEntity category) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM category WHERE id = ?")) {
            statement.setObject(1, category.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
