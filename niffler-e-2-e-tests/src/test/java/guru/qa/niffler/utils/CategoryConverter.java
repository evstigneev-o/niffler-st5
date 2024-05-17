package guru.qa.niffler.utils;

import guru.qa.niffler.data.repository.SpendRepositoryJdbc;

import java.util.UUID;

public class CategoryConverter {
    private static final SpendRepositoryJdbc spendRepository = new SpendRepositoryJdbc();

    public static UUID getCategoryIdByCategoryAndUsername(String category, String username){
        System.out.println("Категория " + spendRepository.getCategoryByUsernameAndCategory(username, category).getCategory());
        return spendRepository.getCategoryByUsernameAndCategory(username, category).getId();
    }

    public static String getCategoryNameById(UUID id){
        return spendRepository.getCategoryById(id).getCategory();
    }

}
