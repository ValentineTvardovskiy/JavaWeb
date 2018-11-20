package company.controller.admin;

import company.controller.Controller;
import company.model.Product;
import company.service.ProductService;
import company.web.Request;
import company.web.ViewModel;

import java.util.List;

public class GetAllProductsAdminController implements Controller {

    private ProductService productService;

    public GetAllProductsAdminController(ProductService productService) {
        this.productService = productService;
    }


    @Override
    public ViewModel process(Request request) {
        List<Product> products = productService.findAll();
        ViewModel vm = ViewModel.of("manageProducts");
        vm.addAttribute("products", products);
        return vm;
    }
}
