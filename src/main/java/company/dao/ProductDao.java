package company.dao;

import company.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    void save (Product product);

    Product findByName(String name);

    List<Product> findAll();

}
