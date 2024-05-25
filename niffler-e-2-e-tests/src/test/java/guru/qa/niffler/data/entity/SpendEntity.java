package guru.qa.niffler.data.entity;

import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import static guru.qa.niffler.utils.CategoryConverter.getCategoryIdByCategoryAndUsername;

@Getter
@Setter
public class SpendEntity implements Serializable {

    private UUID id;
    private String username;
    private CurrencyValues currency;
    private Date spendDate;
    private Double amount;
    private String description;
    private UUID category;

    public static SpendEntity fromJson(SpendJson spendJson) {
        SpendEntity entity = new SpendEntity();
        entity.setId(spendJson.id());
        entity.setUsername(spendJson.username());
        entity.setCurrency(spendJson.currency());
        entity.setSpendDate(spendJson.spendDate());
        entity.setAmount(spendJson.amount());
        entity.setDescription(spendJson.description());
        entity.setCategory(getCategoryIdByCategoryAndUsername(spendJson.username(),spendJson.category()));
        return entity;
    }
}
