package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.repository.SpendRepository;
import guru.qa.niffler.model.CategoryJson;

import static guru.qa.niffler.data.entity.CategoryEntity.fromJson;
import static guru.qa.niffler.model.CategoryJson.fromEntity;

public class CategoryJdbcExtension extends AbstractCategoryExtension {

    private final SpendRepository spendRepository = SpendRepository.getInstance();

    @Override
    protected CategoryJson createCategory(CategoryJson category) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategory(category.category());
        categoryEntity.setUsername(category.username());
        categoryEntity = spendRepository.createCategory(categoryEntity);
        return fromEntity(categoryEntity);
    }

    @Override
    protected void removeCategory(CategoryJson category) {
        spendRepository.removeCategory(fromJson(category));
    }
}
