package http;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {
    private final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private String requestLine;
    private Map<String, String> requestHeader;
    private String requestBody;

    public HttpRequest(String requestLine, Map<String, String> requestHeader, String requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public String getUri() {
        String uri = requestLine.split(" ")[1];
        if (uri.contains("\\?")) {
            logger.debug(uri.split("\\?")[0]);
            return uri.split("\\?")[0];
        }
        return uri;
    }

    public Map<String, String> getQueryMap() {
        //userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
        Map<String, String> queryMap = new HashMap<>();
        String uri = requestLine.split(" ")[1];
        String[] params = uri.split("\\?")[1].split("&");
        for (String param : params) {
            String[] split = param.split("=");
            queryMap.put(split[0], split[1]);
        }
        return queryMap;
    }
}