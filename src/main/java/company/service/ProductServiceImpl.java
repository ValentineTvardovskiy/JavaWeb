package company.service;

import company.dao.ProductDao;
import company.model.Product;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public void save(Product product) {
        productDao.save(product);
    }

    @Override
    public Product findByName(String name) {
        return productDao.findByName(name);
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }
}
