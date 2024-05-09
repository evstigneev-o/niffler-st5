package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.model.SpendJson;

public abstract class AbstractSpendExtension {
    protected abstract SpendJson createSpend(SpendJson spend);

    protected abstract void removeSpend(SpendJson spend);
}
