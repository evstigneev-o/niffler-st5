package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.entity.UserAuthEntity;
import guru.qa.niffler.data.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    static UserRepository getInstance() {
        String repo = System.getProperty("repo");
        if ("sjdbc".equals(repo)) {
            return new UserRepositorySpringJdbc();
        }
        if ("hibernate".equals(repo)) {
            return new UserRepositoryHibernate();
        }
        return new UserRepositoryJdbc();
    }

    UserAuthEntity createUserInAuth(UserAuthEntity user);

    UserEntity createUserInUserdata(UserEntity user);

    UserAuthEntity updateUserInAuth(UserAuthEntity user);

    UserEntity updateUserInUserdata(UserEntity user);

    UserAuthEntity getUserFromAuthByUsername(String username);

    UserEntity getUserFromUserdataByUsername(String username);

    Optional<UserEntity> findUserInUserdataById(UUID id);

}
