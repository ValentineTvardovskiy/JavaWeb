package company.web;

import company.model.Category;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewModel {

    private final String view;
    private final Map<String, Object> model = new HashMap<>();
    private Cookie cookie;


    public String getView() {
        return view;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public ViewModel(String view) {
        this.view = view;
    }

    public void addAttribute(String name, Object obj) {
        this.model.put(name, obj);
    }

    public static ViewModel of(String view) {
        return new ViewModel(view);
    }


    public Cookie getCookie() {
        return cookie;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }

}
