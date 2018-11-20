package company;

import company.dao.CategoryDaoImpl;
import company.model.Category;

public class MainClass {

    public static void main(String[] args) {
        CategoryDaoImpl categoryDao = new CategoryDaoImpl(Factory.getConnection());
        Category category = categoryDao.findById(1L);
        System.out.println(category);



    }
}
