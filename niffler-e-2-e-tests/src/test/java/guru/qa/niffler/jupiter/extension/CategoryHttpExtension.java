package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.CategoriesApi;
import guru.qa.niffler.model.CategoryJson;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.Objects;

public class CategoryHttpExtension extends AbstractCategoryExtension {

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

    private final Retrofit retrofit = new Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://127.0.0.1:8093")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    private final CategoriesApi categoriesApi = retrofit.create(CategoriesApi.class);

    @Override
    protected CategoryJson createCategory(CategoryJson category) {
        CategoryJson categoryJson = new CategoryJson(
                null,
                category.category(),
                category.username()
        );
        try {
            System.out.println("Create category" + categoryJson.toString());
            return Objects.requireNonNull(categoriesApi.addCategory(categoryJson).execute().body());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void removeCategory(CategoryJson category) {

    }
}
