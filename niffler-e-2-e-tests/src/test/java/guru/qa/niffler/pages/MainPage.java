package guru.qa.niffler.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.pages.components.ReactCalendar;

import java.util.Date;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MainPage extends BasePage<MainPage> {
    public static final String URL = CFG.frontUrl() + "main";

    private final ReactCalendar calendar = new ReactCalendar();

    private final SelenideElement headerAvatar = $(".header__avatar");

    private final ElementsCollection spendingRows = $(".spendings-table tbody")
            .$$("tr");
    private final SelenideElement deleteSpendingButton = $(".spendings__bulk-actions button");

    private final SelenideElement peoplePage = $("[data-tooltip-id='people']");

    private final SelenideElement friendsPage = $("[data-tooltip-id='friends']");

    public SelenideElement findSpendingByDescription(String description) {
        return spendingRows.find(text(description));
    }

    public MainPage chooseSpending(SelenideElement spending) {
        spending.$$("td").first().scrollIntoView(true).click();
        return this;
    }

    public MainPage deleteSpending() {
        deleteSpendingButton.click();
        return this;
    }

    public void checkSpendingsCount(int size) {
        spendingRows.shouldHave(size(size));
    }

    public PeoplePage openPeoplePage() {
        peoplePage.click();
        return new PeoplePage();
    }

    public FriendsPage openFriendsPage() {
        friendsPage.click();
        return new FriendsPage();
    }

    @Override
    public MainPage checkPageLoaded() {
        headerAvatar.shouldBe(visible);
        return this;
    }

    public MainPage setDate(Date date) {
        calendar.setDate(date);
        return this;
    }
}
