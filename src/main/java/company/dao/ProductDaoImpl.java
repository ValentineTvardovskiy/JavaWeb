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
    public static void main(String[] args) {
        //ProductDaoImpl dao = new ProductDaoImpl(Factory.getConnection());
        //Product product1 = new Product("SAMSUNG", 699.0, "SAMSUNG PHONE");
        //Product product2 = new Product("IMac", 4699.0, "Expensive environment");
        //Product product3 = new Product("Shoes", 25.0, "Brown shoes");

        //dao.save(product1);
        //dao.save(product2);
        //dao.save(product3);

        //List<Product> result = dao.findAll();
        CategoryDaoImpl categoryDao = new CategoryDaoImpl(Factory.getConnection());
        Category category = categoryDao.findById(1L);
        System.out.println(category);



    }
}
