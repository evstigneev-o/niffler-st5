package guru.qa.niffler.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class PeoplePage {

    private final ElementsCollection peopleRows = $(".abstract-table tbody").$$("tr");

    public SelenideElement findUserByName(String userName) {
       return peopleRows.find(text(userName));
    }

    public PeoplePage checkSendedInvite(SelenideElement user){
        user.$$("td").last().shouldHave(text("Pending invitation"));
        return this;
    }

    public PeoplePage checkFriendship(SelenideElement user){
        user.$$("td").last().shouldHave(text("You are friends"));
        return this;
    }

    public PeoplePage checkReceivedInvite(SelenideElement user){
        user.$$("td").last().$(".abstract-table__buttons div")
                .shouldHave(attribute("data-tooltip-id", "submit-invitation"));
        return this;
    }
}
