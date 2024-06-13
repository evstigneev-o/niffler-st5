package guru.qa.niffler.test;

import guru.qa.niffler.pages.ProfilePage;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ProfileTest extends BaseWebTest{

    @Test
    void updateProfileTest() {
        open(ProfilePage.URL, ProfilePage.class)
                .setName()
                .checkMessage("Profile successfully updated")
                .checkFields();
        $(".dsa").shouldHave(text(""));
    }
}
