package company;

import company.controller.GetAllCategoriesController;
import company.controller.PageNotFoundController;
import company.dao.CategoryDao;
import company.dao.CategoryDaoImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Factory {

    static Connection connection = null;

    static {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static GetAllCategoriesController getAllCategoriesController() {
        return  new GetAllCategoriesController(getCategoryDaoImpl(getConnection()));
    }

    public static CategoryDaoImpl getCategoryDaoImpl(Connection connection) {
        return new CategoryDaoImpl(connection);
    }

    public static PageNotFoundController getPageNotFoundController() {
        return new  PageNotFoundController();
    }
}
