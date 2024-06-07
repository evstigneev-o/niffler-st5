package guru.qa.niffler.pages.components;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.security.Key;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.codeborne.selenide.Selenide.$;

public class ReactCalendar extends BaseComponent<ReactCalendar> {

    public ReactCalendar(SelenideElement self) {
        super(self);
    }

    public ReactCalendar() {
        super($(".react-datepicker"));
    }

    private final SelenideElement inputContainer = $(".react-datepicker__input-container input");
    private final SelenideElement prevMonthButton = self.$(".react-datepicker__navigation--previous");
    private final SelenideElement nextMonthButton = self.$(".react-datepicker__navigation--next");
    private final SelenideElement currentMonthAndYear = self.$(".react-datepicker__current-month");

    public void setDate(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        inputContainer.click();
        setYear(calendar.get(Calendar.YEAR));
        setMonth(calendar.get(Calendar.MONTH));
        setDayOfMonth(calendar.get(Calendar.DATE));

    }

    private void setYear(int year) {
        int currentYear = getCurrentYear();
        if (currentYear != year) {
            if (currentYear > year) {
                while (currentYear != year) {
                    prevMonthButton.click();
                    currentYear = getCurrentYear();
                }
            }
            if (currentYear < year) {
                while (currentYear != year) {
                    nextMonthButton.click();
                    currentYear = getCurrentYear();
                }
            }
        }
    }

    private void setMonth(int monthIndex) {
        int currentMonthIdx = Month.valueOf(getCurrentMonth()).ordinal();
        if(currentMonthIdx != monthIndex) {
            if(currentMonthIdx > monthIndex) {
                while ( currentMonthIdx != monthIndex) {
                    prevMonthButton.click();
                    currentMonthIdx = Month.valueOf(getCurrentMonth()).ordinal();
                }
            }
            if(currentMonthIdx < monthIndex) {
                while ( currentMonthIdx != monthIndex) {
                    nextMonthButton.click();
                    currentMonthIdx = Month.valueOf(getCurrentMonth()).ordinal();
                }
            }
        }
    }

    private void setDayOfMonth(int dayOfMonth) {
        self.$(String.format(".react-datepicker__day--0%02d",dayOfMonth)).click();
    }

    private int getCurrentYear() {
        return Integer.parseInt(currentMonthAndYear.getText().split(" ")[1]);
    }

    private String getCurrentMonth() {
        return currentMonthAndYear.getText().split(" ")[0].toUpperCase();
    }
}
