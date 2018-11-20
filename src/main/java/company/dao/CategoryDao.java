package company.dao;

import company.model.Category;

import java.util.List;

public interface CategoryDao {

    Category findById(Long id);

    List<Category> findAll();
}
