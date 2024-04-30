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

@WebTest
@ExtendWith(UserQueueExtension.class)
public class UsersQueueExampleTest {
    WelcomePage welcomePage = new WelcomePage();

    static {
        Configuration.browserSize = "1920x1080";
        Configuration.browserCapabilities = new ChromeOptions()
                .addArguments("--incognito");
    }

    @Test
    void LoginTest1(UserJson user){
        Selenide.open("http://127.0.0.1:3000/");
        welcomePage
                .openLoginPage()
                .signIn(user.username(), user.testData().password());
        $(".header__avatar").should(visible);
    }
    @Test
    void LoginTest2(UserJson user){
        Selenide.open("http://127.0.0.1:3000/");
        welcomePage
                .openLoginPage()
                .signIn(user.username(), user.testData().password());
        $(".header__avatar").should(visible);
    }
    @Test
    void LoginTest3(UserJson user){
        Selenide.open("http://127.0.0.1:3000/");
        welcomePage
                .openLoginPage()
                .signIn(user.username(), user.testData().password());
        $(".header__avatar").should(visible);
    }
    @Test
    void LoginTest4(UserJson user){
        Selenide.open("http://127.0.0.1:3000/");
        welcomePage
                .openLoginPage()
                .signIn(user.username(), user.testData().password());
        $(".header__avatar").should(visible);
    }
    @Test
    void LoginTest5(UserJson user){
        Selenide.open("http://127.0.0.1:3000/");
        welcomePage
                .openLoginPage()
                .signIn(user.username(), user.testData().password());
        $(".header__avatar").should(visible);
    }
}
