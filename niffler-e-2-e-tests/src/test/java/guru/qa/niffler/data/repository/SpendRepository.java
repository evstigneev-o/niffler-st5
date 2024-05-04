package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.entity.CategoryEntity;

public interface SpendRepository {

    static SpendRepository getInstance(){
        if("sjdbc".equals(System.getProperty("repo"))){
            return new SpendRepositorySpringJdbc();
        }
        if("hibernate".equals(System.getProperty("repo"))){
            return new SpendRepositoryHibernate();
        }
        return new SpendRepositoryJdbc();
    }

    CategoryEntity createCategory(CategoryEntity category);
    void removeCategory(CategoryEntity category);


}
