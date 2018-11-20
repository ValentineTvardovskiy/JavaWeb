package company.controller;

import company.web.Request;
import company.web.ViewModel;

import javax.servlet.http.Cookie;

public class LogoutController implements Controller {

    @Override
    public ViewModel process(Request request) {
        Cookie[] cookies = request.getCookies();
        ViewModel vm = ViewModel.of("login");

        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("MATE")) {
                    cookies[i].setMaxAge(0);
                    vm.setCookie(cookies[i]);
                }
            }
        }
        return vm;
    }
}
