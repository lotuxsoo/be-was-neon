package http;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {
    private final String requestLine;
    private final Map<String, String> headers;
    private final String body;
    private final Map<String, String> paramMap;

    public HttpRequest(String requestLine, Map<String, String> headers, String body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
        this.paramMap = new HashMap<>();
    }

    public String getPath() {
        String path = requestLine.split(" ")[1];
        if (path.contains("?")) {
            return path.split("\\?")[0];
        }
        return path;
    }

    /*
     * 쿼리 스트링을 Map 형태로 변환해서 리턴하는 함수
     * */
    public Map<String, String> getParamMap() {
        String[] params = body.split("&");
        for (String param : params) {
            String[] split = param.split("=");
            paramMap.put(split[0], split[1]);
        }
        return paramMap;
    }
}