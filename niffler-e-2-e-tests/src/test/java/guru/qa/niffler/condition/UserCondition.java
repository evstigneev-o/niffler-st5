package guru.qa.niffler.condition;

import com.codeborne.selenide.WebElementsCondition;
import guru.qa.niffler.model.UserJson;

import java.util.List;

public class UserCondition {
    public static WebElementsCondition usersInTable(UserJson... expectedUsers){
        return new UsersInTableCondition(expectedUsers);
    }
}
