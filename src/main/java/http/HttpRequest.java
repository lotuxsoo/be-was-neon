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

public class HttpRequest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String startLine;
    private final String method;
    private final String resource;
    private Map<String, String> headers;
    private String body;

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        List<String> requests = new ArrayList<>();
        headers = new HashMap<String, String>();

        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            requests.add(line);
        }

        startLine = requests.get(0);
        method = startLine.split(" ")[0];
        resource = startLine.split(" ")[1];

        for (int i = 1; i < requests.size(); i++) {
            String[] split = requests.get(i).split(": ");
            headers.put(split[0], split[1]);
        }

        for (String key : headers.keySet()) {
            logger.debug(key + " " + headers.get(key));
        }
    }

    public String getMethod() {
        return method;
    }

    public String getResource() {
        return resource;
    }
}
