package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestManager {
    private final Logger logger = LoggerFactory.getLogger(RequestManager.class);
    private final BufferedReader br;

    public RequestManager(InputStream in) {
        br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    }

    public HttpRequest readRequest() throws IOException {
        String requestLine = br.readLine();
        logger.debug("요청 라인: {}", requestLine);

        Map<String, String> headers = readHeaders();

        String body = "";
        if (headers.containsKey("Content-Length")) {
            int contentLength = Integer.parseInt(headers.get("Content-Length"));
            body = readBody(contentLength);
        }
        logger.debug("요청 바디: {}", body);

        return new HttpRequest(requestLine, headers, body);
    }

    private Map<String, String> readHeaders() throws IOException {
        Map<String, String> headers = new HashMap<String, String>();

        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            String[] split = line.split(": ");
            headers.put(split[0], split[1]);
        }

        logger.debug("요청 헤더: ");
        for (String key : headers.keySet()) {
            logger.debug(key + " " + headers.get(key));
        }

        return headers;
    }

    private String readBody(int contentLength) throws IOException {
        if (contentLength == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < contentLength; i++) {
            sb.append((char) br.read());
        }

        return URLDecoder.decode(sb.toString(), StandardCharsets.UTF_8);
    }
}