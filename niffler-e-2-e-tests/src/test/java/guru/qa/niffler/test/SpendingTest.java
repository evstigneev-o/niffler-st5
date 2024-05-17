package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.jupiter.annotation.meta.WebHttpTest;
import guru.qa.niffler.jupiter.annotation.meta.WebJdbcTest;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.pages.MainPage;
import guru.qa.niffler.pages.WelcomePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;

@WebJdbcTest
//@WebHttpTest
public class SpendingTest {
    private final WelcomePage welcomePage = new WelcomePage();
    private final MainPage mainPage = new MainPage();
    private final String USERNAME = "oleg";
    private final String PASSWORD = "1234";
    private final String CATEGORY = "Обучение";


    static {
        Configuration.browserSize = "1920x1080";
        Configuration.browserCapabilities = new ChromeOptions()
                .addArguments("--incognito");
    }

    @BeforeEach
    void doLogin() {
        // createSpend
        Selenide.open("http://127.0.0.1:3000/");
        welcomePage
                .openLoginPage()
                .signIn(USERNAME, PASSWORD);
    }


    @GenerateCategory(
            username = USERNAME,
            category = CATEGORY
    )
    @GenerateSpend(
            description = "QA.GURU Advanced 5",
            amount = 65000.00,
            currency = CurrencyValues.RUB
    )
    @Test
    void spendingShouldBeDeletedAfterTableAction(SpendJson spendJson) {
        var spending = mainPage.findSpendingByDescription(spendJson.description());
        mainPage.chooseSpending(spending)
                .deleteSpending()
                .checkSpendingsCount(0);
    }
}
