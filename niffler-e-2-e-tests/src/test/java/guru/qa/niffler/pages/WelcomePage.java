package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class WelcomePage extends BasePage<WelcomePage> {

    public static final String URL = CFG.frontUrl();

    private final SelenideElement mainHeader = $(".main__header");

    private final SelenideElement loginButton = $(byText("Login"));

    public LoginPage openLoginPage() {
        loginButton.click();
        return new LoginPage();
    }

    @Override
    public WelcomePage checkPageLoaded() {
        mainHeader.shouldBe(visible);
        return this;
    }
}
