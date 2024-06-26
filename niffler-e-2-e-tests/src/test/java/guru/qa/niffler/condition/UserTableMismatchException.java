package guru.qa.niffler.condition;

import com.codeborne.selenide.ex.UIAssertionError;
import com.codeborne.selenide.impl.CollectionSource;

import javax.annotation.Nullable;

import static java.lang.System.lineSeparator;

public class UserTableMismatchException extends UIAssertionError {
    public UserTableMismatchException(String message, CollectionSource collection,
                                  String expectedValue, String actualElementText,
                                  @Nullable String explanation, long timeoutMs,
                                  @Nullable Throwable cause) {
        super(
                collection.driver(),
                message +
                        lineSeparator() + "Actual: " + actualElementText +
                        lineSeparator() + "Expected: " + expectedValue +
                        (explanation == null ? "" : lineSeparator() + "Because: " + explanation) +
                        lineSeparator() + "Collection: " + collection.description(),
                expectedValue, actualElementText,
                cause,
                timeoutMs);
    }

}
