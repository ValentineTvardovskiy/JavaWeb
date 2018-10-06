package company.controller;

import company.web.Request;
import company.web.ViewModel;

public class PageNotFoundController implements Controller {

    @Override
    public ViewModel process(Request request) {
        return ViewModel.of("404");
    }
}
