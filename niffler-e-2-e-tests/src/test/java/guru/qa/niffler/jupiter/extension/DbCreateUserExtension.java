package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.data.entity.UserAuthEntity;
import guru.qa.niffler.data.entity.UserEntity;
import guru.qa.niffler.data.repository.UserRepository;
import guru.qa.niffler.data.repository.logging.AllureJsonAppender;
import guru.qa.niffler.model.UserJson;

public class DbCreateUserExtension extends AbstractCreateUserExtension {
    private final UserRepository userRepository = UserRepository.getInstance();
    private final AllureJsonAppender allureJsonAppender = new AllureJsonAppender();

    @Override
    protected UserJson createUser(UserJson user) {
        UserAuthEntity userAuthEntity = UserAuthEntity.fromJson(user);
        UserEntity userEntity = UserEntity.fromJson(user);
        allureJsonAppender.logJson("userAuthEntity", userAuthEntity);
        allureJsonAppender.logJson("userEntity", userEntity);
        userRepository.createUserInAuth(userAuthEntity);
        userRepository.createUserInUserdata(userEntity);
        return user;
    }
}
