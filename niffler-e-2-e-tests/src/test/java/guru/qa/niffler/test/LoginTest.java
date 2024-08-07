package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.data.entity.*;
import guru.qa.niffler.data.repository.UserRepository;
import guru.qa.niffler.data.repository.UserRepositoryHibernate;
import guru.qa.niffler.jupiter.annotation.TestUser;
import guru.qa.niffler.jupiter.extension.DbCreateUserExtension;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.pages.WelcomePage;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(DbCreateUserExtension.class)
public class LoginTest extends BaseWebTest {
    UserRepository userRepository = new UserRepositoryHibernate();
    UserEntity userDataUser;

//    @BeforeEach
//    void createUserForTest() {
//        AuthorityEntity read = new AuthorityEntity();
//        read.setAuthority(Authority.read);
//        AuthorityEntity write = new AuthorityEntity();
//        write.setAuthority(Authority.write);
//
//
//        UserAuthEntity user = new UserAuthEntity();
//        user.setUsername("jdbc_user19");
//        user.setPassword("12345");
//        user.setEnabled(true);
//        user.setAccountNonExpired(true);
//        user.setAccountNonLocked(true);
//        user.setCredentialsNonExpired(true);
//        user.addAuthorities(
//                read, write
//        );
//
//        userRepository.createUserInAuth(user);
//
//        UserEntity userEntity = new UserEntity();
//        userEntity.setUsername("jdbc_user19");
//        userEntity.setCurrency(CurrencyValues.RUB);
//        userDataUser = userRepository.createUserInUserdata(userEntity);
//    }

    @Test
    @TestUser
    void loginTest(UserJson userJson) {
        Allure.getLifecycle().addAttachment("UserJson","application/json",".json",userJson.toString().getBytes());
        Selenide.open(WelcomePage.URL, WelcomePage.class)
                .checkPageLoaded()
                .openLoginPage()
                .checkPageLoaded()
                .signIn(userJson.username(),userJson.testData().password())
                .checkPageLoaded();
        assertTrue(false);


    }

}
