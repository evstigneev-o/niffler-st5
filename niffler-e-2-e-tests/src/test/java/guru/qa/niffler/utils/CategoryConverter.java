package guru.qa.niffler.utils;

import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.repository.SpendRepositoryJdbc;

import java.util.UUID;

public class CategoryConverter {
    private static final SpendRepositoryJdbc spendRepository = new SpendRepositoryJdbc();

    public static CategoryEntity getCategoryIdByCategoryAndUsername(String category, String username){
        return spendRepository.getCategoryByUsernameAndCategory(username, category);
    }

    public static String getCategoryNameById(UUID id){
        return spendRepository.getCategoryById(id).getCategory();
    }

}
