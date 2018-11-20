package company.dao;

import company.model.Category;
import company.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl extends AbstractDao<Product> implements ProductDao{

    public ProductDaoImpl(Connection connection) {
        super(connection);
    }

    public void save(Product product) {
        String query = "INSERT INTO PRODUCTS (NAME, PRICE, DESCRIPTION, FK_CATEGORIES) VALUES (?, ?, ?, ?);";
        PreparedStatement statement;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setString(3, product.getDescription());
            statement.setLong(4, product.getCategory().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Product findByName(String name) {
        String query = "SELECT ID, NAME, PRICE, DESCRIPTION FROM PRODUCTS WHERE NAME = ?";
        PreparedStatement statement;
        ResultSet resultSet;
        Product product = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            product = resultSet.next() ? getProductFromResultSet(resultSet) : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    public List<Product> findAll() {
        String query = "SELECT P.ID, P.NAME, P.PRICE, P.DESCRIPTION, C.CATEGORY_NAME FROM PRODUCTS P" +
                " JOIN CATEGORIES C ON P.FK_CATEGORIES = C.ID";
        List<Product> result = new ArrayList<>();
        Statement statement;
        ResultSet resultSet;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {

                result.add(getProductsWithCategoryFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Product getProductsWithCategoryFromResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product(
                resultSet.getLong(1),
                resultSet.getString(2),
                resultSet.getDouble(3),
                resultSet.getString(4));

        Category category = new Category();
        category.setName(resultSet.getString(5));

        product.setCategory(category);
        return product;
    }

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getLong(1),
                resultSet.getString(2),
                resultSet.getDouble(3),
                resultSet.getString(4));
    }
}
