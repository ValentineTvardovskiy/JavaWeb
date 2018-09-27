package company.controller;

import company.web.Requeast;
import company.web.ViewModel;

public class PageNotFoundController implements Controller {

    @Override
    public ViewModel process(Requeast requeast) {
        return ViewModel.of("404");
    }
}
