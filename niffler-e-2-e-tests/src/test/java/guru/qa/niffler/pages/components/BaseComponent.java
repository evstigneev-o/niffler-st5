package guru.qa.niffler.pages.components;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

@Getter
public abstract class BaseComponent<T extends BaseComponent<?>>{
    protected final SelenideElement self;

    public BaseComponent(SelenideElement self) {
        this.self = self;
    }

}
