package company.dao;

import company.Factory;
import company.model.Category;
import company.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl extends AbstractDao<Product> implements ProductDao{
    public ProductDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    protected Product getObjectFromResultSet(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getLong(1),
                resultSet.getString(2),
                resultSet.getDouble(3),
                resultSet.getString(4));
    }

    public Product findByName(String name) {
        String query = "SELECT * FROM Products WHERE NAME = ?";
        PreparedStatement statement;
        ResultSet resultSet;
        Product product = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            product = resultSet.next() ? getObjectFromResultSet(resultSet) : null;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }
}
