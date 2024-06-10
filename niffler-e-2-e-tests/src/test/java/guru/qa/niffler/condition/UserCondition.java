package guru.qa.niffler.condition;

import com.codeborne.selenide.WebElementsCondition;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.model.UserJson;

public class UserCondition {
    public static WebElementsCondition spendsInTable(UserJson... expectedUsers){
        return new UsersInTableCondition(expectedUsers);
    }
}
