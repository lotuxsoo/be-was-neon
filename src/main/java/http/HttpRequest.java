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
    private String method;
    private String resource;
    private Map<String, String> requestHeader;
    private Map<String, String> requestBody;

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        this.requestHeader = readRequestHeader(br);
        if (requestHeader.containsKey("Content-Length")) {
            this.requestBody = readRequestBody(br);
        }
    }

    private Map<String, String> readRequestHeader(BufferedReader br) throws IOException {
        requestHeader = new HashMap<String, String>();
        List<String> requests = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            requests.add(line);
        }

        this.requestLine = requests.get(0);
        this.method = requestLine.split(" ")[0];
        this.resource = requestLine.split(" ")[1];

        for (int i = 1; i < requests.size(); i++) {
            String[] split = requests.get(i).split(": ");
            requestHeader.put(split[0], split[1]);
        }

        for (String key : requestHeader.keySet()) {
            logger.debug(key + " " + requestHeader.get(key));
        }
        return requestHeader;
    }

    private Map<String, String> readRequestBody(BufferedReader br) throws IOException {
        requestBody = new HashMap<String, String>();
        int contentLength = Integer.parseInt(requestHeader.get("Content-Length"));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < contentLength; i++) {
            sb.append((char) br.read());
        }

        User user = User.makeUser(sb.toString());
        logger.debug("User: " + user.toString());

        return requestBody;
    }

    public String getMethod() {
        return method;
    }

    public String getResource() {
        return resource;
    }
}
