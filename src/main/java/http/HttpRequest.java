package http;

import db.Database;
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
    //    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String startLine;
    //    private String method;
//    private String path;
//    private String version;
    private Map<String, String> queryMap = new HashMap<String, String>();

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        List<String> requests = new ArrayList<String>();
        String line;
        while (!(line = br.readLine()).isEmpty()) {
            requests.add(line);
        }

        for (String request : requests) {
            logger.debug(request);
        }

        startLine = requests.get(0);

    }

//    public HttpRequest(String requestLine) throws UnsupportedEncodingException {
//        String[] split = requestLine.split(" ");
//        this.method = split[0];
//        this.path = URLDecoder.decode(split[1], StandardCharsets.UTF_8);
//        this.version = split[2];
//    }

    public User createUser() {
        String queryParameter = getTokens(getPath(), "\\?")[1];
        String[] params = getTokens(queryParameter, "&");
        for (int i = 0; i < params.length; i++) {
            String[] tokens = getTokens(params[i], "=");
            queryMap.put(tokens[0], tokens[1]);
        }

        User user = new User(queryMap.get("userId"), queryMap.get("password"), queryMap.get("name"),
                queryMap.get("email"));
        Database.addUser(user);
        return Database.findUserById(user.getUserId());
    }

    public String[] getTokens(String str, String separator) {
        return str.split(separator);
    }

    public String getPath() {
        return getTokens(startLine, " ")[1];
    }
}
