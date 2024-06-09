package guru.qa.niffler.test;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.jupiter.annotation.Spends;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.pages.MainPage;
import guru.qa.niffler.pages.WelcomePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static guru.qa.niffler.condition.SpendsCondition.spendsInTable;


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
    @Spends({
            @GenerateSpend(
                    description = "QA.GURU Advanced 5 - обучение",
                    amount = 65000.00,
                    currency = CurrencyValues.RUB
            ),
            @GenerateSpend(
                    description = "QA.GURU Advanced 5 - написание диплома",
                    amount = 1.00,
                    currency = CurrencyValues.RUB
            )
    })
    @Test
    void spendingShouldBeDeletedAfterTableAction(SpendJson spendJson) {
        var spending = mainPage.findSpendingByDescription(spendJson.description());
        mainPage.chooseSpending(spending)
                .deleteSpending()
                .checkSpendingsCount(2);
    }

    @GenerateCategory(
            username = USERNAME,
            category = CATEGORY
    )
    @Spends({
            @GenerateSpend(
                    description = "QA.GURU Advanced 5 - обучение",
                    amount = 65000.00,
                    currency = CurrencyValues.RUB
            ),
            @GenerateSpend(
                    description = "QA.GURU Advanced 5 - написание диплома",
                    amount = 1.00,
                    currency = CurrencyValues.RUB
            )
    })
    @Test
    void checkSpendingContent(SpendJson[] spends){
        ElementsCollection spendings = $(".spendings-table tbody")
                .$$("tr");
        spendings.get(0).scrollIntoView(true);
        spendings.shouldHave(spendsInTable(spends));
    }
}
