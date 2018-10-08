package company.controller;

import company.dao.ProductDao;
import company.dao.ProductDaoImpl;
import company.model.Category;
import company.model.Product;
import company.web.Request;
import company.web.ViewModel;

import java.sql.SQLException;

public class GetProductByIdController implements Controller {
    private final ProductDaoImpl productDaoImpl;
    public GetProductByIdController(ProductDaoImpl productDaoImpl) {
        this.productDaoImpl = productDaoImpl;
    }
    @Override
    public ViewModel process(Request request) throws SQLException {
        Product product = productDaoImpl.findById(getIdFromRequest(request));
        ViewModel vm = ViewModel.of("product");
        vm.addAttribute("product", product);
        return vm;
    }
    private Long getIdFromRequest(Request request) {
        String iDobject = request.getParamByName("p_id");
        return Long.valueOf(iDobject);
    }
}
