package guru.qa.niffler.condition;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.WebElementsCondition;
import com.codeborne.selenide.impl.CollectionSource;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.utils.DateUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class SpendsInTableCondition extends WebElementsCondition {

    private final SpendJson[] expectedSpends;

    public SpendsInTableCondition(SpendJson[] expectedSpends) {
        this.expectedSpends = expectedSpends;
    }

    @Nonnull
    @Override
    public CheckResult check(Driver driver, List<WebElement> elements) {
        if (elements.size() != expectedSpends.length) {
            return CheckResult.rejected(
                    "Spending table size mismatch",
                    elements.size()
            );
        }

        for (int i = 0; i < elements.size(); i++) {
            WebElement row = elements.get(i);
            SpendJson expectedSpendForRow = expectedSpends[i];

            List<WebElement> td = row.findElements(By.cssSelector("td"));

            boolean dateResult = td.get(1).getText().contains(
                    DateUtils.formatDate(
                            expectedSpendForRow.spendDate(),
                            "dd MMM yy"
                    )
            );

            if (!dateResult) {
                return CheckResult.rejected(
                        "Spending table: date mismatch",
                        td.get(1).getText()
                );
            }

            boolean amountResult = Double.valueOf(td.get(2).getText()).equals(expectedSpendForRow.amount());

            if (!amountResult) {
                return CheckResult.rejected(
                        "Spending table: amount mismatch",
                        td.get(2).getText()
                );
            }

            boolean currencyResult = td.get(3).getText().contains(
                    expectedSpendForRow.currency().name()
            );

            if (!currencyResult) {
                return CheckResult.rejected(
                        "Spending table: currency mismatch",
                        td.get(3).getText()
                );
            }

            boolean categoryResult = td.get(4).getText().contains(
                    expectedSpendForRow.category()
            );

            if (!categoryResult) {
                return CheckResult.rejected(
                        "Spending table: category mismatch",
                        td.get(4).getText()
                );
            }

            boolean descriptionResult = td.get(5).getText().contains(
                    expectedSpendForRow.description()
            );

            if (!descriptionResult) {
                return CheckResult.rejected(
                        "Spending table: description mismatch",
                        td.get(5).getText()
                );
            }

        }
        return CheckResult.accepted();
    }

    @Override
    public void fail(CollectionSource collection, CheckResult lastCheckResult, @Nullable Exception cause, long timeoutMs) {
        String actualElementText = lastCheckResult.getActualValue();

        String message = lastCheckResult.getMessageOrElse(() -> "Spending mismatch");
        throw new SpendMismatchException(message, collection, Arrays.toString(expectedSpends), actualElementText, explanation, timeoutMs, cause);
    }

    @Override
    public String toString() {
        return "";
    }
}
