package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.SpendApiClient;
import guru.qa.niffler.model.SpendJson;

import java.util.List;
import java.util.Objects;

public class SpendHttpExtension extends AbstractSpendExtension{

    private final SpendApiClient spendApiClient = new SpendApiClient();

    @Override
    protected SpendJson createSpend(SpendJson spend) {
        try {
            return Objects.requireNonNull(spendApiClient.createSpend(spend));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected SpendJson editSpend(SpendJson spend) {
        try {
            return Objects.requireNonNull(spendApiClient.editSpend(spend));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void removeSpend(SpendJson spend){
        spendApiClient.removeSpend(spend.username(), List.of(spend.id().toString()));
    }
}
