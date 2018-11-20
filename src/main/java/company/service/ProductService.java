package company.service;

import company.dao.ProductDao;
import company.model.Product;

import java.util.List;

public interface ProductService {

    void add(Product product);

    Product findByName(String name);

    ProductDao getProductDao();

//    List<Product> findAll();

}
