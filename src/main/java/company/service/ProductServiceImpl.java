package company.service;

import company.dao.ProductDao;
import company.dao.ProductDaoImpl;
import company.model.Product;

import java.util.List;

public class ProductServiceImpl implements ProductService {


    private final ProductDao productDao;

    public ProductServiceImpl (ProductDao productDao){
        this.productDao = productDao;
    }

    @Override
    public void add(Product product) {
        productDao.add(product);
    }

    @Override
    public Product findByName(String name) {
        return productDao.findByName(name);
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    //
//    @Override
//    public List<Product> findAll() {
//        return productDao.findAll();
//    }
}
