package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.TestUser;
import guru.qa.niffler.jupiter.extension.DbCreateUserExtension;
import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@ExtendWith(DbCreateUserExtension.class)
public class LoginTest {


    @Test
    @TestUser
    void loginTest(UserJson userJson) {
        Selenide.open("http://127.0.0.1:3000/");
        $("a[href*='redirect']").click();
        $("input[name='username']").setValue(userJson.username());
        $("input[name='password']").setValue(userJson.testData().password());
        $("button[type='submit']").click();
        $(".header__avatar").should(visible);

    }

}
