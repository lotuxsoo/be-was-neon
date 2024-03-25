package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String requestLine;
    private Map<String, String> requestHeader;
    private String requestBody;

    public HttpRequest(String requestLine, Map<String, String> requestHeader, String requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

//    private Map<String, String> readRequestBody(BufferedReader br) throws IOException {
//
//        int contentLength = Integer.parseInt(requestHeader.get("Content-Length"));
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < contentLength; i++) {
//            sb.append((char) br.read());
//        }
//
//        User user = User.makeUser(sb.toString());
//        logger.debug("User: " + user.toString());
//
//        return requestBody;
//    }
}
