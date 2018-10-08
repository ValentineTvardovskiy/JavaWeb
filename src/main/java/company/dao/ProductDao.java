package company.dao;

import company.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    void add(Product product);
    List<Product> findAll() throws SQLException;
    Product findByName(String name);
    Product findById(Long id);

}
