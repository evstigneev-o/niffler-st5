package guru.qa.niffler.api;

import guru.qa.niffler.model.SpendJson;

import java.util.List;

public class SpendApiClient extends ApiClient {

    private final SpendApi spendApi;

    public SpendApiClient() {
        super(CFG.spendUrl());
        this.spendApi = retrofit.create(SpendApi.class);
    }

    public SpendJson createSpend(SpendJson spendJson) throws Exception {
        return spendApi.createSpend(spendJson)
                .execute()
                .body();
    }

    public SpendJson editSpend(SpendJson spendJson) throws Exception {
        return spendApi.editSpend(spendJson)
                .execute()
                .body();
    }

    public SpendJson removeSpend(String username, List<String> ids) {
        return null;
    }
}
