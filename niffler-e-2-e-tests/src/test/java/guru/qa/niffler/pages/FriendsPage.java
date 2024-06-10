package guru.qa.niffler.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class FriendsPage extends BasePage<FriendsPage> {

    private final SelenideElement peopleTable = $(".abstract-table");
    private final ElementsCollection peopleRows = $(".abstract-table tbody").$$("tr");

    @Override
    public FriendsPage checkPageLoaded() {
        peopleTable.shouldBe(visible);
        return this;
    }
}
