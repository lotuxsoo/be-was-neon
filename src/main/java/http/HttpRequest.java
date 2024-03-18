package http;

import static utils.StringUtil.getTokens;

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

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        List<String> requests = new ArrayList<>();
        String line;
        while (!(line = br.readLine()).isEmpty()) {
            requests.add(line);
        }

        startLine = requests.get(0);

        for (String request : requests) {
            logger.debug(request);
        }
    }

    public String getUri() {
        return getTokens(startLine, " ")[1];
    }
}
