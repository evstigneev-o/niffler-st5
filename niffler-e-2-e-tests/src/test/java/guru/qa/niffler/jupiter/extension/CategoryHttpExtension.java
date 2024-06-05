package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.CategoriesApiClient;
import guru.qa.niffler.model.CategoryJson;

import java.util.Objects;

public class CategoryHttpExtension extends AbstractCategoryExtension {
    private final CategoriesApiClient categoriesApiClient = new CategoriesApiClient();

    @Override
    protected CategoryJson createCategory(CategoryJson category) {
        CategoryJson categoryJson = new CategoryJson(
                null,
                category.category(),
                category.username()
        );
        try {
            return Objects.requireNonNull(categoriesApiClient.addCategory(categoryJson));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void removeCategory(CategoryJson category) {

    }
}
