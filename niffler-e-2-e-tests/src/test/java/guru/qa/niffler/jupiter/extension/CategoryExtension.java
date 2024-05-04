package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.CategoriesApi;
import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.repository.SpendRepository;
import guru.qa.niffler.data.repository.SpendRepositoryJdbc;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.model.CategoryJson;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.Objects;

public class CategoryExtension implements BeforeEachCallback, AfterEachCallback {

    public static final ExtensionContext.Namespace NAMESPACE =
            ExtensionContext.Namespace.create(CategoryExtension.class);

//    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
//
//    private final Retrofit retrofit = new Retrofit.Builder()
//            .client(okHttpClient)
//            .baseUrl("http://127.0.0.1:8093")
//            .addConverterFactory(JacksonConverterFactory.create())
//            .build();
//    private final CategoriesApi categoriesApi = retrofit.create(CategoriesApi.class);

    private final SpendRepository spendRepository = SpendRepository.getInstance();

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        AnnotationSupport.findAnnotation(extensionContext.getRequiredTestMethod(), GenerateCategory.class)
                .ifPresent(
                        cat -> {
                            CategoryEntity category = new CategoryEntity();
                            category.setCategory(cat.category());
                            category.setUsername(cat.username());

                            spendRepository.createCategory(category);

                            extensionContext.getStore(NAMESPACE).put(extensionContext.getUniqueId(), CategoryJson.fromEntity(category));

                        }
                );
    }

    @Override
    public void afterEach(ExtensionContext context) {
       CategoryJson categoryJson = context.getStore(NAMESPACE).get(context.getUniqueId(), CategoryJson.class);
       spendRepository.removeCategory(CategoryEntity.fromJson(categoryJson));
    }
}
