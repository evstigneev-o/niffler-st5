package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.meta.WebHttpTest;
import guru.qa.niffler.jupiter.annotation.meta.WebJdbcTest;
import org.openqa.selenium.chrome.ChromeOptions;

@WebHttpTest
//@WebJdbcTest
public class BaseWebTest {
    protected static final Config CFG = Config.getInstance();

    static {
        Configuration.browserSize = "1920x1080";
        Configuration.browserCapabilities = new ChromeOptions()
                .addArguments("--incognito");
    }
}
