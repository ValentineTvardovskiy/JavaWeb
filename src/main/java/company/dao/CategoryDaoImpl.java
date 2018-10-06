package company.dao;

import company.model.Category;
import company.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl extends AbstractDao<Category> implements CategoryDao {
    public CategoryDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Category findById(Long id) {
        String query = "SELECT C.ID, C.CATEGORY_NAME, P.ID, P.NAME, P.PRICE, P.DESCRIPTION " +
                "FROM CATEGORIES C JOIN PRODUCTS P " +
                "ON C.ID = P.FK_CATEGORIES " +
                "WHERE C.ID = ?";
        PreparedStatement statement;
        ResultSet resultSet;
        Category result = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            result = resultSet.next() ? getObjectFromResultSetWithProduct(resultSet) : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected Category getObjectFromResultSet(ResultSet resultSet) throws SQLException {
        return new Category(
                resultSet.getLong(1),
                resultSet.getString(2));
    }

    private Category getObjectFromResultSetWithProduct(ResultSet resultSet) throws SQLException {
        List<Product> products = new ArrayList<>();
        Category result = new Category(
                resultSet.getLong(1),
                resultSet.getString(2),
                products);
        while (!resultSet.isAfterLast()) {
            Product product = new Product(
                    resultSet.getLong(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5),
                    resultSet.getString(6));
            products.add(product);
            resultSet.next();
        }
        return result;
    }
}
