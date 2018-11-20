package company.controller.admin;

import company.controller.Controller;
import company.model.Category;
import company.model.Product;
import company.service.ProductService;
import company.web.Request;
import company.web.ViewModel;

import java.sql.SQLException;
import java.util.List;

public class AddProductController implements Controller {

    private final ProductService productService;

    public AddProductController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ViewModel process(Request request) {
        String productName = request.getParamByName("productName");
        double price = Double.valueOf(request.getParamByName("price"));
        String description = request.getParamByName("description");
        long categoryId = Long.valueOf(request.getParamByName("categoryId"));

        Product product = new Product(productName, price, description);
        Category category = new Category();
        category.setId(categoryId);
        product.setCategory(category);

        productService.save(product);

        List<Product> products = productService.findAll();

        ViewModel vm = ViewModel.of("manageProducts");
        vm.addAttribute("products", products);

        return vm;
    }
}
