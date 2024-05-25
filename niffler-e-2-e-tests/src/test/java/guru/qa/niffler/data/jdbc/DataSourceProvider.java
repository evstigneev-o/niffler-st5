package guru.qa.niffler.data.jdbc;

import guru.qa.niffler.data.DataBase;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum DataSourceProvider {
    INSTANCE;

    private final Map<DataBase, DataSource> store = new ConcurrentHashMap<>();

    private DataSource computeDataSource(DataBase database) {
       return store.computeIfAbsent(database, key ->{
           PGSimpleDataSource pgDataSource = new PGSimpleDataSource();
           pgDataSource.setUrl(database.getJdbcUrl());
           pgDataSource.setUser("postgres");
           pgDataSource.setPassword("secret");
           return pgDataSource;
       });
    }

    public static DataSource dataSource(DataBase database) {
        return DataSourceProvider.INSTANCE.computeDataSource(database);
    }
}
