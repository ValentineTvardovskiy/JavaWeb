package company.controller;

import company.web.Request;
import company.web.ViewModel;

import java.sql.SQLException;

public interface Controller {

    ViewModel process(Request request);

}
