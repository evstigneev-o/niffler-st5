package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.jupiter.extension.UserQueueExtension;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.pages.WelcomePage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

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
