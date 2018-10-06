package company.dao;

import company.model.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {

    Category findById(Long id);

    List<Category> findAll() throws SQLException;
}
