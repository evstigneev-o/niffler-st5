package guru.qa.niffler.api;

import guru.qa.niffler.model.SpendJson;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface SpendApi {
    @POST("internal/spends/add")
    Call<SpendJson> createSpend(@Body SpendJson spendJson);

    @PATCH("internal/spends/edit")
    Call<SpendJson> editSpend(@Body SpendJson spendJson);

    @DELETE("internal/spends/remove")
    Call<SpendJson> removeSpend(@Query("username") String username, @Query("ids") List<String> ids);
}
