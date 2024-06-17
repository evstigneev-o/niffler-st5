package guru.qa.niffler.jupiter.extension;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.TestResult;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class AllureLogsExtension implements SuiteExtension {

    public static final String caseName = "logs";

    @Override
    public void afterSuite() {
        String caseId = UUID.randomUUID().toString();
        AllureLifecycle lifecycle = Allure.getLifecycle();
        lifecycle.scheduleTestCase(
                new TestResult()
                        .setUuid(caseId)
                        .setName("logs")
                        .setTestCaseName("logs")
                        .setStatus(Status.PASSED)
                );
        lifecycle.startTestCase(caseId);
        try {
            lifecycle.addAttachment("auth log", "text/html", ".log", Files.newInputStream(
                    Path.of("./auth.log")
            ));
            lifecycle.addAttachment("currency log", "text/html", ".log", Files.newInputStream(
                    Path.of("./currency.log")
            ));
            lifecycle.addAttachment("spend log", "text/html", ".log", Files.newInputStream(
                    Path.of("./spend.log")
            ));
            lifecycle.addAttachment("userdata log", "text/html", ".log", Files.newInputStream(
                    Path.of("./userdata.log")
            ));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        lifecycle.stopTestCase(caseId);
        lifecycle.writeTestCase(caseId);

    }

}
