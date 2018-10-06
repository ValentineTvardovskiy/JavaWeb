package company.controller;

import company.dao.CategoryDao;
import company.model.Category;
import company.web.Request;
import company.web.ViewModel;

public class GetCategoryByIdController implements Controller{
    private final CategoryDao categoryDao;

    public GetCategoryByIdController(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public ViewModel process(Request request) {
        Category category = categoryDao.findById(getIdFromRequest(request));
        ViewModel vm = ViewModel.of("category");
        vm.addAttribute("category", category);
        return vm;
    }

    private Long getIdFromRequest(Request request) {
        String iDobject = request.getParamByName("c_id");
        return Long.valueOf(iDobject);
    }
}
