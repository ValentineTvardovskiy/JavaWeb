package company.controller;

import company.dao.CategoryDao;
import company.model.Category;
import company.web.Request;
import company.web.ViewModel;

import java.sql.SQLException;
import java.util.List;

public class GetAllCategoriesController implements Controller {

    private final CategoryDao categoryDao;

    public GetAllCategoriesController(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public ViewModel process(Request request) throws SQLException {
        List<Category> categories = categoryDao.findAll();
        ViewModel vm = ViewModel.of("categories");
        vm.addAttribute("categories", categories);
        return vm;
    }
}
