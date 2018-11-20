package company.web.filters;

import company.dao.UserDao;
import company.model.Role.RoleName;
import company.model.User;
import company.model.Role;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static company.Factory.getUserDao;
import static company.model.Role.RoleName.ADMIN;
import static company.model.Role.RoleName.USER;

public class UserFilter implements Filter {

    private final Map<String, RoleName> protectedUriMap = new HashMap<>();
    private UserDao userDao;

    public static final String COOKIE_NAME = "MATE";

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
            processUnauthenticated(request, response);
            return;
        }

        if (roleName == null) {
            processAuthenticated(request, response, chain);
            return;
        }

        for (Cookie c : cookies) {
            if (c.getName().equals(COOKIE_NAME)) {
                token = c.getValue();
            }
        }

        if (token == null) {
            processUnauthenticated(request, response);
        } else {
            user = userDao.findByToken(token);
            if (user == null) {
                processUnauthenticated(request, response);
            } else {
                setUser(req, user);
                if (verifyRole(user, roleName)) {
                    processAuthenticated(request, response, chain);
                } else {
                    processAccessDenied(request, response);
                }
            }
        }
    }

    private void setUser(HttpServletRequest req, User user) {
        req.setAttribute("user", user);
    }

    private boolean verifyRole(User user, RoleName roleName) {
        return user.getRoles().stream()
                .map(Role::getRoleName)
                .anyMatch(r -> r.equals(roleName));
    }

    private void processAccessDenied(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp").forward(request, response);
    }

    private void processAuthenticated(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }

    private void processUnauthenticated(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    public void destroy() {

    }
}
