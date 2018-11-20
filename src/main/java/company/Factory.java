package company;

import company.controller.*;
import company.controller.admin.AddProductController;
import company.controller.admin.GetAllCategoriesAdminController;
import company.controller.admin.GetAllProductsAdminController;
import company.dao.*;
import company.service.ProductService;
import company.service.ProductServiceImpl;
import company.service.UserServiceImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Factory {

    private static Connection connection;

    static {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:h2:tcp://localhost/~/test", "sa", "");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static GetAllCategoriesController getAllCategoriesController() {
        return new GetAllCategoriesController(getCategoryDaoImpl(getConnection()));
    }

    public static GetAllCategoriesAdminController getAllCategoriesAdminController(String viewName) {
        return new GetAllCategoriesAdminController(getCategoryDaoImpl(getConnection()), viewName);
    }

    public static GetAllProductsAdminController getAllProductsAdminController() {
        return new GetAllProductsAdminController(getProductService(getConnection()));
    }

    public static AddProductController getAddProductController() {
        return new AddProductController(getProductService(getConnection()));
    }

    public static ProductService getProductService(Connection connection) {
        return new ProductServiceImpl(getProductDao(connection));
    }

    private static ProductDao getProductDao(Connection connection) {
        return new ProductDaoImpl(connection);
    }

    public static CategoryDaoImpl getCategoryDaoImpl(Connection connection) {
        return new CategoryDaoImpl(connection);
    }

    public static LoginController getLoginPageController() {
        return new LoginController(getUserService());
    }

    public static LogOutController getLogOutController() {
        return new LogOutController();
    }

    public static RegisterController getRegisterController() {
        return new RegisterController(getUserService());
    }

    public static UserServiceImpl getUserService() {
        return new UserServiceImpl(getUserDao());
    }

    public static UserDaoImpl getUserDao() {
        return new UserDaoImpl(getConnection());
    }
}
