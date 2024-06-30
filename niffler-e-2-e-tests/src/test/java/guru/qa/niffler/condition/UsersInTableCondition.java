package guru.qa.niffler.condition;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.WebElementsCondition;
import com.codeborne.selenide.impl.CollectionSource;
import guru.qa.niffler.model.FriendState;
import guru.qa.niffler.model.UserJson;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class UsersInTableCondition extends WebElementsCondition {
    private String expectedResult;
    private final UserJson[] expectedUsers;

    public UsersInTableCondition(UserJson[] expectedUsers) {
        this.expectedUsers = expectedUsers;
    }

    @Nonnull
    @Override
    public CheckResult check(Driver driver, List<WebElement> elements) {
        String actualResult;
        if (elements.size() != expectedUsers.length) {
            expectedResult = String.valueOf(expectedUsers.length);
            actualResult = String.valueOf(elements.size());
            return CheckResult.rejected(
                    "User table size mismatch",
                    actualResult
            );
        }

        for (int i = 0; i < elements.size(); i++) {
            WebElement row = elements.get(i);
            UserJson expectedUserForRow = expectedUsers[i];

            List<WebElement> td = row.findElements(By.cssSelector("td"));

            boolean avatarResult;
            String actualPhoto = td.get(0).findElement(By.cssSelector("img")).getAttribute("src");
            String expectedPhoto = expectedUserForRow.photoSmall();
            if (actualPhoto.contains("images/niffler_avatar.jpeg")) {
                avatarResult = expectedPhoto == null;
            } else avatarResult = actualPhoto.equals(expectedPhoto);
            if (!avatarResult) {
                expectedResult = expectedPhoto;
                actualResult = actualPhoto;
                return CheckResult.rejected(
                        "User table: avatar mismatch",
                        actualResult
                );
            }

            boolean usernameResult = td.get(1).getText().equals(expectedUserForRow.username());
            if (!usernameResult) {
                expectedResult = expectedUserForRow.username();
                actualResult = td.get(1).getText();
                return CheckResult.rejected(
                        "User table: username mismatch",
                        actualResult
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
                expectedResult = expectedUserForRow.firstname() + " " + expectedUserForRow.surname();
                actualResult = actualName;
                return CheckResult.rejected(
                        "User table: name mismatch",
                        actualResult
                );
            }

            boolean actionsResult;
            String actualActions = getActions(td.get(3));
            actionsResult = switch (actualActions) {
                case "You are friends" -> expectedUserForRow.friendState().equals(FriendState.FRIEND);
                case "Add friend" -> expectedUserForRow.friendState() == null;
                case "Pending invitation" -> expectedUserForRow.friendState().equals(FriendState.INVITE_SENT);
                case "Submit invitation" -> expectedUserForRow.friendState().equals(FriendState.INVITE_RECEIVED);
                default -> false;
            };
            if (!actionsResult) {
                expectedResult = expectedUserForRow.friendState().toString();
                actualResult = actualActions;
                return CheckResult.rejected(
                        "User table: actions mismatch",
                        actualResult
                );
            }
        }
        return CheckResult.accepted();
    }

    @Override
    public void fail(CollectionSource collection, CheckResult lastCheckResult, @Nullable Exception cause, long timeoutMs) {
        String actualElementText = lastCheckResult.getActualValue();

        String message = lastCheckResult.getMessageOrElse(() -> "Table mismatch");
        throw new UserTableMismatchException(message, collection, expectedResult, actualElementText, explanation, timeoutMs, cause);
    }

    @Override
    public String toString() {
        return "";
    }

    private String getActions(WebElement row) {
        return row.getText().isBlank() ?
                row.findElement(By.cssSelector("div[data-tooltip-id]")).getAttribute("data-tooltip-content")
                : row.getText();
    }

}
