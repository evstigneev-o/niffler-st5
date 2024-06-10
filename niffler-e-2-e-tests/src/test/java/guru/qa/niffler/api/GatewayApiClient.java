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

    public CategoryJson addCategory(String bearerToken, CategoryJson category) throws Exception {
        return gatewayApi.addCategory(bearerToken, category).execute().body();
    }

    public List<CurrencyJson> getCurrencies(String bearerToken) throws Exception {
        return gatewayApi.getCurrencies(bearerToken).execute().body();
    }

    public List<UserJson> getFriends(String bearerToken) throws Exception {
        return gatewayApi.getFriends(bearerToken).execute().body();
    }

    public void removeFriend(String bearerToken, String targetUsername) throws Exception {
        gatewayApi.removeFriend(bearerToken, targetUsername).execute();
    }

    public List<UserJson> getIncomeInvitations(String bearerToken, String searchQuery) throws Exception {
        return gatewayApi.getIncomeInvitations(bearerToken, searchQuery).execute().body();
    }

    public List<UserJson> getOutcomeInvitations(String authToken, String searchQuery) throws Exception {
        return gatewayApi.getOutcomeInvitations(authToken, searchQuery).execute().body();
    }

    public UserJson sendInvitation(String bearerToken, FriendJson friend) throws Exception {
        return gatewayApi.sendInvitation(bearerToken, friend).execute().body();
    }

    public UserJson acceptInvitation(String bearerToken, FriendJson invitation) throws Exception {
        return gatewayApi.acceptInvitation(bearerToken, invitation).execute().body();
    }

    public UserJson declineInvitation(String bearerToken, FriendJson invitation) throws Exception {
        return gatewayApi.declineInvitation(bearerToken, invitation).execute().body();
    }

    public SessionJson getSession(String bearerToken) throws Exception {
        return gatewayApi.getSession(bearerToken).execute().body();
    }

    public List<SpendJson> getSpends(String bearerToken, DataFilterValues filterPeriod, CurrencyValues filterCurrency) throws Exception {
        return gatewayApi.getSpends(bearerToken, filterPeriod, filterCurrency).execute().body();
    }

    public SpendJson addSpend(String bearerToken, SpendJson spend) throws Exception {
        return gatewayApi.addSpend(bearerToken, spend).execute().body();
    }

    public SpendJson editSpend(String bearerToken, SpendJson spend) throws Exception {
        return gatewayApi.editSpend(bearerToken, spend).execute().body();
    }

    public void deleteSpends(String bearerToken, List<String> ids) throws Exception {
        gatewayApi.deleteSpends(bearerToken, ids).execute();
    }

    public List<StatisticJson> getTotalStatistic(String bearerToken, CurrencyValues filterCurrency, DataFilterValues filterPeriod) throws Exception {
        return gatewayApi.getTotalStatistic(bearerToken, filterCurrency, filterPeriod).execute().body();
    }

    public UserJson getCurrentUser(String bearerToken) throws Exception {
        return gatewayApi.getCurrentUser(bearerToken).execute().body();
    }

    public List<UserJson> getAllUsers(String bearerToken, String searchQuery) throws Exception {
        return gatewayApi.getAllUsers(bearerToken, searchQuery).execute().body();
    }

    public UserJson updateUserInfo(String bearerToken, UserJson user) throws Exception {
        return gatewayApi.updateUserInfo(bearerToken, user).execute().body();
    }
}
