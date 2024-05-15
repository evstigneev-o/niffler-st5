package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.DataBase;
import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.jdbc.DataSourceProvider;
import guru.qa.niffler.model.CurrencyValues;

import javax.sql.DataSource;
import java.sql.*;
import java.util.UUID;

public class SpendRepositoryJdbc implements SpendRepository {

    private static final DataSource spendDataSource = DataSourceProvider.dataSource(DataBase.SPEND);

    @Override
    public CategoryEntity createCategory(CategoryEntity category) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO category(category,username) VALUES (?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS
             )) {
            ps.setString(1, category.getCategory());
            ps.setString(2, category.getUsername());
            ps.executeUpdate();

            UUID generatedId;
            try (ResultSet resultSet = ps.getGeneratedKeys()) {
                if (resultSet.next()) {
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
    public CategoryEntity editCategory(CategoryEntity category) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement us = connection.prepareStatement(
                     "UPDATE category SET category=?, username=? WHERE id=?");
             PreparedStatement ss = connection.prepareStatement(
                     "SELECT * FROM category WHERE id=?")) {

            us.setString(1, category.getCategory());
            us.setString(2, category.getUsername());
            us.setObject(3, category.getId());

            int rowsAffected = us.executeUpdate();
            if (rowsAffected > 0) {
                ss.setObject(1, category.getId());
                try (ResultSet resultSet = ss.executeQuery()) {
                    if (resultSet.next()) {
                        category.setId(UUID.fromString(resultSet.getString("id")));
                        category.setCategory(resultSet.getString("category"));
                        category.setUsername(resultSet.getString("username"));
                    }
                }
            }
            return category;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeCategory(CategoryEntity category) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "DELETE FROM category WHERE id = ?")) {
            ps.setObject(1, category.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public SpendEntity createSpend(SpendEntity spend) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO spend(username,spend_date,currency,amount,description,category_id) VALUES (?, ?, ?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS
             )) {
            ps.setString(1, spend.getUsername());
            ps.setObject(2, new java.sql.Date(spend.getSpendDate().getTime()));
            ps.setObject(3, spend.getCurrency().name());
            ps.setDouble(4, spend.getAmount());
            ps.setString(5, spend.getDescription());
            ps.setObject(6, spend.getCategory());
            ps.executeUpdate();
            UUID generatedId;
            try (ResultSet resultSet = ps.getGeneratedKeys()) {
                if (resultSet.next()) {
                    generatedId = UUID.fromString(resultSet.getString("id"));
                } else throw new IllegalStateException("Can't access to id");
            }
            spend.setId(generatedId);
            return spend;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SpendEntity editSpend(SpendEntity spend) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement us= connection.prepareStatement(
                     "UPDATE spend SET username=?, spend_date=?, currency=?, amount=?, description =?, category_id=? WHERE id=?");
             PreparedStatement ss = connection.prepareStatement(
                     "SELECT * FROM spend WHERE id=?")) {
            us.setString(1, spend.getUsername());
            us.setObject(2, new java.sql.Date(spend.getSpendDate().getTime()));
            us.setObject(3, spend.getCurrency().name());
            us.setDouble(4, spend.getAmount());
            us.setString(5, spend.getDescription());
            us.setObject(6, spend.getCategory());
            us.setObject(7, spend.getId());
            int rowsAffected = us.executeUpdate();
            System.out.println(rowsAffected);
            SpendEntity newSpend = new SpendEntity();
            if (rowsAffected > 0) {
                ss.setObject(1, spend.getId());
                try (ResultSet resultSet = ss.executeQuery()) {
                    if (resultSet.next()) {
                        newSpend.setId(UUID.fromString(resultSet.getString("id")));
                        newSpend.setUsername(resultSet.getString("username"));
                        newSpend.setSpendDate(resultSet.getDate("spend_date"));
                        newSpend.setCurrency(CurrencyValues.valueOf(resultSet.getString("currency")));
                        newSpend.setAmount(resultSet.getDouble("amount"));
                        newSpend.setDescription(resultSet.getString("description"));
                        newSpend.setCategory(UUID.fromString(resultSet.getString("category_id")));
                    }
                }
            }
            return newSpend;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeSpend(SpendEntity spend) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "DELETE FROM spend WHERE id = ?")) {
            ps.setObject(1, spend.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
