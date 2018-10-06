package company.web;

import java.util.Map;
import java.util.Objects;

public class Request {

    private final String method;
    private final String uri;
    private final Map<String, String[]> params;



    public static Request of(String request, String uri)
    {
        return new Request(request, uri, null);
    }

    public static Request of(String request, String uri, Map<String, String[]> params) {
        return new Request(request, uri, params);
    }

    public Request(String method, String uri, Map<String, String[]> params) {
        this.method = method;
        this.uri = uri;
        this.params = params;
    }
    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getParamByName(String param) {
        return params.get(param)[0];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(method, request.method) &&
                Objects.equals(uri, request.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, uri);
    }
}
