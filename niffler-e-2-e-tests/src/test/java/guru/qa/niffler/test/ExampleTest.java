package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.pages.WelcomePage;
import org.junit.jupiter.api.Test;
import wiremock.net.minidev.json.JSONUtil;

import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.open;

public class ExampleTest extends BaseWebTest{

    @Test
    void setDateTest(){
        open(WelcomePage.URL,WelcomePage.class)
                .checkPageLoaded()
                .openLoginPage()
                .checkPageLoaded()
                .signIn("jdbc_user18","12345")
                .checkPageLoaded()
                .setDate(new Date(122, Calendar.JUNE,12))
                .deleteSpending();

    }


    @Test
    void setTimeTest(){
        GregorianCalendar gc = new GregorianCalendar(2024,Calendar.JUNE,4);
        var one = gc.get(Calendar.MONTH);
        String pick = String.format("react-datepicker__day react-datepicker__day--0%02d",11);

        System.out.println(pick);
    }
}
