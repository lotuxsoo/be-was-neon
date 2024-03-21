package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String startLine;
    private final String method;
    private final String resource;

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        List<String> requests = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            requests.add(line);
        }

        startLine = requests.get(0);
        method = startLine.split(" ")[0];
        resource = startLine.split(" ")[1];
        for (String request : requests) {
            logger.debug(request);
        }

    }

    public String getMethod() {
        return method;
    }

    public String getResource() {
        return resource;
    }
}
