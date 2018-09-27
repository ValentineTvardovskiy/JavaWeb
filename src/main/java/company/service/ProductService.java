package company.service;

import company.model.Product;

import java.util.List;

public interface ProductService {

    void save(Product product);
    Product findByName(String name);
    List<Product> findAll();
}
