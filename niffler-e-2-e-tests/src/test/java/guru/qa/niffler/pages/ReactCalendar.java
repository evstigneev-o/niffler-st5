package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class ReactCalendar extends BaseComponent<ReactCalendar> {

    private final String inputContainer = ".react-datepicker__input-container";

    private final  SelenideElement dateInput = $(inputContainer).$("[type=text]");

    public ReactCalendar(SelenideElement self) {
        super(self);
    }

    public ReactCalendar() {
        super($(".react-datepicker"));
    }

    public ReactCalendar setDate(String date) {
        self.click();
        dateInput.setValue(date);
        return this;
    }
}
