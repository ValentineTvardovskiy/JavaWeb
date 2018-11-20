package company.dao;

import java.sql.Connection;

public abstract class AbstractDao<T> {

    final Connection connection;

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }

}