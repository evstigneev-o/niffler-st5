package guru.qa.niffler.api;

import guru.qa.niffler.model.CategoryJson;

public class CategoriesApiClient extends ApiClient {

    private final CategoriesApi categoriesApi;

    public CategoriesApiClient() {
        super(CFG.spendUrl());
        this.categoriesApi = retrofit.create(CategoriesApi.class);
    }

    public CategoryJson addCategory(CategoryJson category) throws Exception{
        return categoriesApi.addCategory(category).execute().body();
    }
}
