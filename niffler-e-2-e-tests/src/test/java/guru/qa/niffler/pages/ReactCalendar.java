package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class ReactCalendar extends BaseComponent<ReactCalendar> {

    public ReactCalendar(SelenideElement self) {
        super(self);
    }

    public ReactCalendar() {
        super($(".react-datepicker"));
    }

    public ReactCalendar setDate(String date) {
//        self.$(...) // строка с датой..
        return this;
    }
}
