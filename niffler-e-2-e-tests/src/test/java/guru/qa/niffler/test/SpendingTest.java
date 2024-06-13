package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.pages.MainPage;
import guru.qa.niffler.pages.WelcomePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class SpendingTest extends BaseWebTest {
    private final MainPage mainPage = new MainPage();
    private final String USERNAME = "oleg";
    private final String PASSWORD = "1234";
    private final String CATEGORY = "Обучение";


    @BeforeEach
    void doLogin() {
        // createSpend
        Selenide.open(WelcomePage.URL,WelcomePage.class)
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
