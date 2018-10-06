package company.web;

import company.dao.UserDao;
import company.model.User;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static company.Factory.getUserDao;

public class UserFilter implements Filter {

    private final Set<String> protectedUriSet = new HashSet<>();
    private UserDao userDao;
    private static final  String COOKIE_NAME = "MATE";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userDao = getUserDao();
        protectedUriSet.add("/servlet/categories");
        protectedUriSet.add("/servlet/category");
        protectedUriSet.add("/servlet/products");
        protectedUriSet.add("/servlet/product");
        protectedUriSet.add("/servlet/home");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        Cookie[] cookies = req.getCookies();
        String token = null;
        User user = null;

        if(!protectedUriSet.contains(req.getRequestURI())) {
            processAutorized(request, response, chain);
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
                req.setAttribute("user_id", user.getId());
                processAutorized(request, response, chain);
            }
        }
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
