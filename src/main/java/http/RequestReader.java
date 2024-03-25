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

    public RequestReader(InputStream in) {
        br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    }

    public HttpRequest readRequest() throws IOException {
        String requestLine = br.readLine();
        logger.debug(requestLine);

        Map<String, String> requestHeader = readHeaders();

        int contentLength = Integer.parseInt(requestHeader.get("Content-Length"));
        String requestBody = readBody(contentLength);

        return new HttpRequest(requestLine, requestHeader, requestBody);
    }

    private Map<String, String> readHeaders() throws IOException {
        Map<String, String> requestHeader = new HashMap<String, String>();

        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            String[] split = line.split(": ");
            requestHeader.put(split[0], split[1]);
        }

        for (String key : requestHeader.keySet()) {
            logger.debug(key + " " + requestHeader.get(key));
        }

        return requestHeader;
    }

    private String readBody(int contentLength) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < contentLength; i++) {
            sb.append((char) br.read());
        }
        return sb.toString();
    }
}
