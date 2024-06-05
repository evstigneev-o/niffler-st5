package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.extension.UserQueueExtension;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.pages.WelcomePage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(UserQueueExtension.class)
public class UsersQueueExampleTest extends BaseWebTest {

    @Test
    void LoginTest1(UserJson user) {
        Selenide.open(WelcomePage.URL, WelcomePage.class)
                .openLoginPage()
                .signIn(user.username(), user.testData().password())
                .checkPageLoaded();
    }

    @Test
    void LoginTest2(UserJson user) {
        Selenide.open(WelcomePage.URL, WelcomePage.class)
                .openLoginPage()
                .signIn(user.username(), user.testData().password())
                .checkPageLoaded();
    }

    @Test
    void LoginTest3(UserJson user) {
        Selenide.open(WelcomePage.URL, WelcomePage.class)
                .openLoginPage()
                .signIn(user.username(), user.testData().password())
                .checkPageLoaded();
    }

    @Test
    void LoginTest4(UserJson user) {
        Selenide.open(WelcomePage.URL, WelcomePage.class)
                .openLoginPage()
                .signIn(user.username(), user.testData().password())
                .checkPageLoaded();
    }

    @Test
    void LoginTest5(UserJson user) {
        Selenide.open(WelcomePage.URL, WelcomePage.class)
                .openLoginPage()
                .signIn(user.username(), user.testData().password())
                .checkPageLoaded();
    }
}
