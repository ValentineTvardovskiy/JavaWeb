package company.controller;

import company.web.Requeast;
import company.web.ViewModel;

public interface Controller {

    ViewModel process(Requeast requeast);

}
