package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.SpendApi;
import guru.qa.niffler.api.SpendApiClient;
import guru.qa.niffler.model.SpendJson;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class SpendHttpExtension extends AbstractSpendExtension{

//    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
//            .build();
//
//    private final Retrofit retrofit = new Retrofit.Builder()
//            .client(okHttpClient)
//            .baseUrl("http://127.0.0.1:8093/")
//            .addConverterFactory(JacksonConverterFactory.create())
//            .build();
//
//    private final SpendApi spendApi = retrofit.create(SpendApi.class);

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
    protected void removeSpend(SpendJson spend) {
        spendApiClient.removeSpend(spend.username(), List.of(spend.id().toString()));
    }
}
