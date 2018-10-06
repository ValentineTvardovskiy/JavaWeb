package company.controller;

import company.model.User;
import company.service.UserService;
import company.web.Request;
import company.web.ViewModel;

import javax.servlet.http.Cookie;

public class LoginController implements Controller {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ViewModel process(Request request) {
        String email = request.getParamByName("email");
        String password = request.getParamByName("password");
        User user = userService.findByEmail(email);

        boolean isVerified = userService.validatePassword(user, password);

        ViewModel vm = null;
        if (user != null && isVerified) {
            vm = processAutorised(user);
        } else {
            vm = processUnautorised();
        }
        return vm;
    }

    private ViewModel processAutorised(User user) {
        ViewModel vm = ViewModel.of("home");
        Cookie cookie = new Cookie("MATE", user.getToken());
        vm.setCookie(cookie);
        vm.addAttribute("user", user);
        return vm;
    }

    private ViewModel processUnautorised() {
        ViewModel vm = ViewModel.of("login");
        vm.addAttribute("msg", true);
        return vm;
    }
}
