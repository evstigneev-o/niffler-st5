package guru.qa.niffler.condition;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.WebElementsCondition;
import com.codeborne.selenide.impl.CollectionSource;
import guru.qa.niffler.model.UserJson;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class UsersInTableCondition extends WebElementsCondition {
    private String bug;
    private final UserJson[] expectedUsers;

    public UsersInTableCondition(UserJson[] expectedUsers) {
        this.expectedUsers = expectedUsers;
    }

    @Nonnull
    @Override
    public CheckResult check(Driver driver, List<WebElement> elements) {
        if (elements.size() != expectedUsers.length) {
            return CheckResult.rejected(
                    "User table size mismatch",
                    String.valueOf(elements.size())
            );
        }

        for (int i = 0; i < elements.size(); i++) {
            WebElement row = elements.get(i);
            UserJson expectedUserForRow = expectedUsers[i];

            List<WebElement> td = row.findElements(By.cssSelector("td"));

            boolean avatarResult;
            String actualPhoto = td.get(0).findElement(By.cssSelector("img")).getAttribute("src");
            String expectedPhoto = expectedUserForRow.photoSmall();
            if(actualPhoto.contains("images/niffler_avatar.jpeg")){
                avatarResult = expectedPhoto == null;
            } else avatarResult = actualPhoto.equals(expectedPhoto);
            if(!avatarResult){
                return CheckResult.rejected(
                        "User table: avatar mismatch " + expectedPhoto,null
                       // actualPhoto
                );
            }

            boolean usernameResult = td.get(1).getText().equals(expectedUserForRow.username());
            if (!usernameResult) {
                return CheckResult.rejected(
                        "User table: username mismatch",null
                        //bug = td.get(1).getText()
                );
            }

            boolean nameResult;
            String actualName = td.get(2).getText();
            if (actualName.isBlank()) {
                nameResult = expectedUserForRow.firstname() == null && expectedUserForRow.surname() == null;
            } else {
                nameResult = actualName.equals(expectedUserForRow.firstname() + " " + expectedUserForRow.surname());
            }
            if (!nameResult) {
                return CheckResult.rejected(
                        "User table: user mismatch",null
                        //bug = td.get(2).getText()
                );
            }
            var s = td.get(3);
            //var actionsResult = td.get(3).getText().equals(expectedUserForRow.friendState());
            int av = 1;


        }
        return CheckResult.accepted();
    }

    @Override
    public void fail(CollectionSource collection, CheckResult lastCheckResult, @Nullable Exception cause, long timeoutMs) {
        String actualElementText = lastCheckResult.getActualValue();

        String message = lastCheckResult.getMessageOrElse(() -> "User mismatch");
        throw new UserTableMismatchException(message, collection, bug, actualElementText, explanation, timeoutMs, cause);
    }

    @Override
    public String toString() {
        return "";
    }
}
