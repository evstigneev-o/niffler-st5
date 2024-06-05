package guru.qa.niffler.api;

import guru.qa.niffler.model.*;

import java.util.List;

public class GatewayApiClient extends ApiClient {

    private final GatewayApi gatewayApi;

    public GatewayApiClient() {
        super(CFG.frontUrl());
        this.gatewayApi = retrofit.create(GatewayApi.class);
    }


    public List<CategoryJson> getCategories(String bearerToken) throws Exception {
        return gatewayApi.getCategories(bearerToken).execute().body();
    }

    public CategoryJson addCategory(String authToken, CategoryJson category) throws Exception {
        return gatewayApi.addCategory(authToken, category).execute().body();
    }

    public List<CurrencyJson> getCurrencies(String bearerToken) throws Exception {
        return gatewayApi.getCurrencies(bearerToken).execute().body();
    }

    public List<UserJson> getFriends(String bearerToken, String searchQuery) throws Exception {
        return gatewayApi.getFriends(bearerToken, searchQuery).execute().body();
    }

    public void removeFriend(String authToken, String targetUsername) throws Exception {
        gatewayApi.removeFriend(authToken, targetUsername).execute();
    }

    public List<UserJson> getIncomeInvitations(String authToken, String searchQuery) throws Exception {
        return gatewayApi.getIncomeInvitations(authToken, searchQuery).execute().body();
    }

    public List<UserJson> getOutcomeInvitations(String authToken, String searchQuery) throws Exception {
        return gatewayApi.getOutcomeInvitations(authToken, searchQuery).execute().body();
    }

    public UserJson sendInvitation(String authToken, FriendJson friend) throws Exception {
        return gatewayApi.sendInvitation(authToken, friend).execute().body();
    }

    public UserJson acceptInvitation(String authToken, FriendJson invitation) throws Exception {
        return gatewayApi.acceptInvitation(authToken, invitation).execute().body();
    }

    public UserJson declineInvitation(String authToken, FriendJson invitation) throws Exception {
        return gatewayApi.declineInvitation(authToken, invitation).execute().body();
    }

    public SessionJson getSession(String authToken) throws Exception {
        return gatewayApi.getSession(authToken).execute().body();
    }

    public List<SpendJson> getSpends(String authToken, DataFilterValues filterPeriod, CurrencyValues filterCurrency) throws Exception {
        return gatewayApi.getSpends(authToken, filterPeriod, filterCurrency).execute().body();
    }

    public SpendJson addSpend(String authToken, SpendJson spend) throws Exception {
        return gatewayApi.addSpend(authToken, spend).execute().body();
    }

    public SpendJson editSpend(String authToken, SpendJson spend) throws Exception {
        return gatewayApi.editSpend(authToken, spend).execute().body();
    }

    public void deleteSpends(String authToken, List<String> ids) throws Exception {
        gatewayApi.deleteSpends(authToken, ids).execute();
    }

    public List<StatisticJson> getTotalStatistic(String authToken, CurrencyValues filterCurrency, DataFilterValues filterPeriod) throws Exception {
        return gatewayApi.getTotalStatistic(authToken, filterCurrency, filterPeriod).execute().body();
    }

    public UserJson getCurrentUser(String authToken) throws Exception {
        return gatewayApi.getCurrentUser(authToken).execute().body();
    }

    public List<UserJson> getAllUsers(String authToken, String searchQuery) throws Exception {
        return null;
    }

    public UserJson updateUserInfo(String authToken, UserJson user) throws Exception {
        return gatewayApi.updateUserInfo(authToken, user).execute().body();
    }
}
