package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.DataBase;
import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.jpa.EmProvider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class SpendRepositoryHibernate implements SpendRepository {

    private final EntityManager em = EmProvider.entityManager(DataBase.SPEND);

    @Override
    public CategoryEntity createCategory(CategoryEntity category) {
        em.persist(category);
        return category;
    }

    @Override
    public CategoryEntity editCategory(CategoryEntity category) {
        return null;
    }

    @Override
    public void removeCategory(CategoryEntity category) {

    }

    @Override
    public SpendEntity createSpend(SpendEntity spend) {
        return null;
    }

    @Override
    public SpendEntity editSpend(SpendEntity spend) {
        return null;
    }

    @Override
    public void removeSpend(SpendEntity spend) {

    }

    @Override
    public List<SpendEntity> findAllByUsername(String username) {
        return List.of();
    }
}
