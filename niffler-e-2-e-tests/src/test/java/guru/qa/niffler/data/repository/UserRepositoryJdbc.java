package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.DataBase;
import guru.qa.niffler.data.entity.*;
import guru.qa.niffler.data.jdbc.DataSourceProvider;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserRepositoryJdbc implements UserRepository {

    private static final DataSource authDataSource = DataSourceProvider.dataSource(DataBase.AUTH);
    private static final DataSource udDataSource = DataSourceProvider.dataSource(DataBase.USERDATA);
    private static final PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Override
    public UserAuthEntity createUserInAuth(UserAuthEntity user) {
        try (Connection conn = authDataSource.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement userPs = conn.prepareStatement(
                    "INSERT INTO \"user\" (" +
                            "username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired)" +
                            " VALUES (?, ?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
                 PreparedStatement authorityPs = conn.prepareStatement(
                         "INSERT INTO \"authority\" (" +
                                 "user_id, authority)" +
                                 " VALUES (?, ?)"
                 )) {
                userPs.setString(1, user.getUsername());
                userPs.setString(2, pe.encode(user.getPassword()));
                userPs.setBoolean(3, user.getEnabled());
                userPs.setBoolean(4, user.getAccountNonExpired());
                userPs.setBoolean(5, user.getAccountNonLocked());
                userPs.setBoolean(6, user.getCredentialsNonExpired());
                userPs.executeUpdate();

                UUID generatedUserId;
                try (ResultSet resultSet = userPs.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        generatedUserId = UUID.fromString(resultSet.getString("id"));
                    } else {
                        throw new IllegalStateException("Can`t access to id");
                    }
                }
                user.setId(generatedUserId);

                for (AuthorityEntity a : user.getAuthorities()) {
                    authorityPs.setObject(1, generatedUserId);
                    authorityPs.setString(2, a.getAuthority().name());
                    authorityPs.addBatch();
                    authorityPs.clearParameters();
                }
                authorityPs.executeBatch();
                conn.commit();
                return user;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserEntity createUserInUserdata(UserEntity user) {
        try (Connection conn = udDataSource.getConnection();
             PreparedStatement userPs = conn.prepareStatement(
                     "INSERT INTO \"user\" (" +
                             "username, currency, firstname, surname, photo, photo_small)" +
                             " VALUES (?, ?, ?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS
             )) {
            userPs.setString(1, user.getUsername());
            userPs.setString(2, user.getCurrency().name());
            userPs.setString(3, user.getFirstname());
            userPs.setString(4, user.getSurname());
            userPs.setObject(5, user.getPhoto());
            userPs.setObject(6, user.getPhotoSmall());
            userPs.executeUpdate();

            UUID generatedUserId;
            try (ResultSet resultSet = userPs.getGeneratedKeys()) {
                if (resultSet.next()) {
                    generatedUserId = UUID.fromString(resultSet.getString("id"));
                } else {
                    throw new IllegalStateException("Can`t access to id");
                }
            }
            user.setId(generatedUserId);
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserAuthEntity updateUserInAuth(UserAuthEntity user) {
        try (Connection conn = authDataSource.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement userPs = conn.prepareStatement(
                    "UPDATE \"user\" SET " +
                            "username = ?, password = ?, enabled = ?, account_non_expired = ?, account_non_locked = ?, credentials_non_expired = ? " +
                            "WHERE id = ?"
            );
                 PreparedStatement deleteAuthorityPs = conn.prepareStatement(
                         "DELETE FROM \"authority\" WHERE user_id = ?"
                 );
                 PreparedStatement authorityPs = conn.prepareStatement(
                         "INSERT INTO \"authority\" (user_id, authority) VALUES (?, ?)"
                 )) {
                userPs.setString(1, user.getUsername());
                userPs.setString(2, pe.encode(user.getPassword()));
                userPs.setBoolean(3, user.getEnabled());
                userPs.setBoolean(4, user.getAccountNonExpired());
                userPs.setBoolean(5, user.getAccountNonLocked());
                userPs.setBoolean(6, user.getCredentialsNonExpired());
                userPs.setObject(7, user.getId());
                userPs.executeUpdate();

                deleteAuthorityPs.setObject(1, user.getId());
                deleteAuthorityPs.executeUpdate();

                for (AuthorityEntity a : user.getAuthorities()) {
                    authorityPs.setObject(1, user.getId());
                    authorityPs.setString(2, a.getAuthority().name());
                    authorityPs.addBatch();
                }
                authorityPs.executeBatch();

                conn.commit();
                return user;
            } catch (SQLException e) {
                conn.rollback();
                throw new SQLException("Error occurred while updating user in auth", e);
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserEntity updateUserInUserdata(UserEntity user) {
        try (Connection conn = udDataSource.getConnection();
             PreparedStatement userPs = conn.prepareStatement(
                     "UPDATE \"user\" SET username=?, currency=?, firstname=?, surname=?, photo=?, photo_small=? WHERE id=?"
             )) {
            userPs.setString(1, user.getUsername());
            userPs.setString(2, user.getCurrency().name());
            userPs.setString(3, user.getFirstname());
            userPs.setString(4, user.getSurname());
            userPs.setObject(5, user.getPhoto());
            userPs.setObject(6, user.getPhotoSmall());
            userPs.setObject(7, user.getId());
            userPs.executeUpdate();
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserAuthEntity getUserFromAuthByUsername(String username) {
        try (Connection conn = authDataSource.getConnection();
             PreparedStatement userPs = conn.prepareStatement(
                     "SELECT * FROM \"user\" WHERE username = ?"
             )) {
            userPs.setString(1, username);
            try (ResultSet resultSet = userPs.executeQuery()) {
                if (resultSet.next()) {
                    UserAuthEntity user = new UserAuthEntity();
                    user.setId(UUID.fromString(resultSet.getString("id")));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setEnabled(resultSet.getBoolean("enabled"));
                    user.setAccountNonExpired(resultSet.getBoolean("account_non_expired"));
                    user.setAccountNonLocked(resultSet.getBoolean("account_non_locked"));
                    user.setCredentialsNonExpired(resultSet.getBoolean("credentials_non_expired"));

                    try (PreparedStatement authorityPs = conn.prepareStatement(
                            "SELECT * FROM \"authority\" WHERE user_id = ?"
                    )) {
                        authorityPs.setObject(1, user.getId());
                        try (ResultSet authorityResultSet = authorityPs.executeQuery()) {
                            List<AuthorityEntity> authorities = new ArrayList<>();
                            while (authorityResultSet.next()) {
                                AuthorityEntity authority = new AuthorityEntity();
                                authority.setId(UUID.fromString(authorityResultSet.getString("id")));
                                authority.setAuthority(Authority.valueOf(authorityResultSet.getString("authority")));
                                authority.setUser(user);
                                authorities.add(authority);
                            }
                            user.setAuthorities(authorities);
                        }
                    }
                    return user;
                }
                throw new SQLException("User not found with username: " + username);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserEntity getUserFromUserdataByUsername(String username) {
        try (Connection conn = udDataSource.getConnection();
             PreparedStatement userPs = conn.prepareStatement(
                     "SELECT * FROM \"user\" WHERE username = ?"
             )) {
            userPs.setString(1, username);
            try (ResultSet resultSet = userPs.executeQuery()) {
                if (resultSet.next()) {
                    UserEntity user = new UserEntity();
                    user.setId(UUID.fromString(resultSet.getString("id")));
                    user.setUsername(resultSet.getString("username"));
                    user.setCurrency(CurrencyValues.valueOf(resultSet.getString("currency")));
                    user.setFirstname(resultSet.getString("firstname"));
                    user.setSurname(resultSet.getString("surname"));
                    user.setPhoto(resultSet.getBytes("photo"));
                    user.setPhotoSmall(resultSet.getBytes("photo_small"));
                    return user;
                }
                throw new SQLException("User not found with username: " + username);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<UserEntity> findUserInUserdataById(UUID id) {
        UserEntity user;
        try (Connection conn = udDataSource.getConnection();
             PreparedStatement userPs = conn.prepareStatement(
                     "SELECT * FROM \"user\" WHERE id=?"
             )) {
            userPs.setObject(1, id);
            ResultSet resultSet = userPs.executeQuery();
            user = new UserEntity();
            if (resultSet.next()) {
                user.setId((UUID) resultSet.getObject("id"));
                user.setUsername(resultSet.getString("username"));
                user.setCurrency(CurrencyValues.valueOf(resultSet.getString("currency")));
                user.setFirstname(resultSet.getString("firstname"));
                user.setSurname(resultSet.getString("surname"));
                user.setPhoto(resultSet.getBytes("photo"));
                user.setPhotoSmall(resultSet.getBytes("photo_small"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(user);
    }
}
