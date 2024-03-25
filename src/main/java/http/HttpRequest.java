package http;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {
    private final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private String requestLine;
    private String method;
    private String path;
    private Map<String, String> headers;
    private String body;
    private Map<String, String> queryMap;

    public HttpRequest(String requestLine, Map<String, String> headers, String body) {
        this.requestLine = requestLine;
        this.method = requestLine.split(" ")[0];
        this.path = requestLine.split(" ")[1];
        this.headers = headers;
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        if (path.contains("?")) {
            return path.split("\\?")[0];
        }
        return path;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public Map<String, String> getQueryMap() {
        //userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
        queryMap = new HashMap<>();
        String[] params = body.split("&");
        for (String param : params) {
            String[] split = param.split("=");
            queryMap.put(split[0], split[1]);
        }
        return queryMap;
    }

    public String getParameter(String name) {
        return queryMap.get(name);
    }
}