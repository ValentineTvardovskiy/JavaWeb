package company.controller.admin;

import company.controller.Controller;
import company.dao.CategoryDao;
import company.dao.ProductDao;
import company.model.Category;
import company.model.Product;
import company.web.Request;
import company.web.ViewModel;

import java.sql.SQLException;
import java.util.List;

public class GetAllProductsAdminController implements Controller {

    private final ProductDao productDao;

    public GetAllProductsAdminController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public ViewModel process(Request request) throws SQLException {
        List<Product> products = productDao.findAll();
        ViewModel vm = ViewModel.of("categories");
        vm.addAttribute("categories", products);
        return vm;
    }
}
