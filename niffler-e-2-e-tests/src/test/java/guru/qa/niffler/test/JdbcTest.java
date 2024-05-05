package guru.qa.niffler.test;

import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.repository.SpendRepository;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

public class JdbcTest {
    private final SpendRepository spendRepository = SpendRepository.getInstance();
    @Test
    void testJdbcCategory() {
        CategoryJson categoryJson = new CategoryJson(
                UUID.fromString("0b189970-c9f6-410a-aeaa-66d84ef31cff"),
                "jdbc2",
                "oleg"
        );
       CategoryEntity category =  spendRepository.editCategory(CategoryEntity.fromJson(categoryJson));
        System.out.println(category.getCategory());
    }

    @Test
    void testJdbcSpend() {
        CategoryJson categoryJson = new CategoryJson(
                null,
                "jdbc4",
                "oleg"
        );
        CategoryJson category =  CategoryJson.fromEntity(spendRepository.createCategory(CategoryEntity.fromJson(categoryJson)));
        SpendJson spendJson = new SpendJson(
                null,
                new Date(),
                category.category(),
                CurrencyValues.RUB,
                6500.0,
                "test from jdbc remove",
                category.username()
        );
        SpendEntity spendEntity = SpendEntity.fromJson(spendJson, category);
        spendRepository.createSpend(spendEntity);
//       spendRepository.removeSpend(spendEntity);
//       spendRepository.removeCategory(CategoryEntity.fromJson(category));
        spendEntity.setDescription("description from test");
        SpendEntity afterUpdate  = spendRepository.editSpend(spendEntity);
        System.out.println(afterUpdate.getDescription());
        System.out.println(afterUpdate.getCurrency());
    }
}
