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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestReader {
    private final BufferedReader br;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String requestLine;
    private Map<String, String> requestHeader;
    private String requestBody;

    public RequestReader(InputStream in) {
        br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    }

    public HttpRequest readRequest() throws IOException {
        this.requestHeader = readHeaders();
        this.requestBody = readBody();
        return new HttpRequest(requestLine, requestHeader, requestBody);
    }

    private Map<String, String> readHeaders() throws IOException {
        requestHeader = new HashMap<String, String>();
        List<String> requests = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            requests.add(line);
        }

        this.requestLine = requests.get(0);
        logger.debug(requestLine);

        for (int i = 1; i < requests.size(); i++) {
            String[] split = requests.get(i).split(": ");
            requestHeader.put(split[0], split[1]);
        }

        for (String key : requestHeader.keySet()) {
            logger.debug(key + " " + requestHeader.get(key));
        }

        return requestHeader;
    }

    private String readBody() throws IOException {
        int contentLength = Integer.parseInt(requestHeader.get("Content-Length"));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < contentLength; i++) {
            sb.append((char) br.read());
        }
        return sb.toString();
    }
}
