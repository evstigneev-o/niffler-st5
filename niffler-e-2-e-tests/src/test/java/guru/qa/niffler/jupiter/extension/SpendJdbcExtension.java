package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.data.repository.SpendRepository;
import guru.qa.niffler.model.SpendJson;

import static guru.qa.niffler.data.entity.SpendEntity.fromJson;

public class SpendJdbcExtension extends AbstractSpendExtension{

    private final SpendRepository spendRepository = SpendRepository.getInstance();

    @Override
    protected SpendJson createSpend(SpendJson spend) {
        return SpendJson.fromEntity(spendRepository.createSpend(fromJson(spend)));
    }

    @Override
    protected SpendJson editSpend(SpendJson spend) {
        return SpendJson.fromEntity(spendRepository.editSpend(fromJson(spend)));
    }

    @Override
    protected void removeSpend(SpendJson spend) {
        spendRepository.removeSpend(fromJson(spend));
    }
}
