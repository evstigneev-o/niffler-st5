package guru.qa.niffler.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class PeoplePage extends BasePage<PeoplePage> {

    private final SelenideElement peopleTable = $(".abstract-table");
    private final ElementsCollection peopleRows = $(".abstract-table tbody").$$("tr");

    public SelenideElement findUserByName(String userName) {
        return peopleRows.find(text(userName));
    }

    public PeoplePage checkSendedInvite(SelenideElement user) {
        user.$$("td").last().shouldHave(text("Pending invitation"));
        return this;
    }

    public PeoplePage checkFriendship() {
        peopleRows.find(text("You are friends")).shouldHave(exist);
        return this;
    }

    public PeoplePage checkReceivedInvite(SelenideElement user) {
        user.$$("td").last().$(".abstract-table__buttons div")
                .shouldHave(attribute("data-tooltip-id", "submit-invitation"));
        return this;
    }

    @Override
    public PeoplePage checkPageLoaded() {
        peopleTable.shouldBe(visible);
        return this;
    }
}
