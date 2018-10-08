package company.web;

import company.dao.UserDao;
import company.model.Role.RoleName;
import company.model.User;
import company.model.Role;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


import static company.Factory.getUserDao;
import static company.model.Role.RoleName.ADMIN;
import static company.model.Role.RoleName.USER;


public class UserFilter implements Filter {

    private final Map<String, RoleName> protectedUriMap = new HashMap<>();
    private UserDao userDao;
    private static final  String COOKIE_NAME = "MATE";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userDao = getUserDao();
        protectedUriMap.put("/servlet/categories", USER);
        protectedUriMap.put("/servlet/category", USER);
        protectedUriMap.put("/servlet/products", USER);
        protectedUriMap.put("/servlet/product", USER);
        protectedUriMap.put("/servlet/home", USER);
        protectedUriMap.put("/servlet/admin", ADMIN);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        Cookie[] cookies = req.getCookies();
        RoleName roleName = protectedUriMap.get(req.getRequestURI());
        String token = null;
        User user = null;

        if (cookies == null) {
            processUnautorized(request, response);
            return;
        }

        if(roleName == null) {
            processAutorized(request, response, chain);
            return;
        }

        for(Cookie c : cookies) {
            if(c.getName().equals(COOKIE_NAME)) {
                token = c.getValue();
            }
        }

        if(token == null) {
            processUnautorized(request, response);

        } else {
            user = userDao.findByToken(token);
            if (user == null) {
                processUnautorized(request, response);
            } else {
                if (verifyRole(user, roleName)) {
                    req.setAttribute("user_id", user.getId());
                    processAutorized(request, response, chain);
                } else {
                    processUnautorized(request, response);
                }
                // req.setAttribute("user_id", user.getId());
            }
        }
    }

    private boolean verifyRole(User user, RoleName roleName) {
        return user.getRoles().stream()
                .map(Role::getRoleName)
                .anyMatch(r -> r.equals(roleName));
    }


    private void processAutorized(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }

    private void processUnautorized(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    public void destroy() {

    }
}
