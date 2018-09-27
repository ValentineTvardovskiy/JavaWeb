package company.controller;

import company.dao.CategoryDao;
import company.model.Category;
import company.web.Requeast;
import company.web.ViewModel;

import java.util.List;

public class GetAllCategoriesController implements Controller{

    private final CategoryDao categoryDao;

    public GetAllCategoriesController(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public ViewModel process(Requeast requeast) {
        List<Category> categories = categoryDao.findAll();
        ViewModel vm = ViewModel.of("categories");
        vm.addAttribute("categories", categories);
        return vm;
    }


}
