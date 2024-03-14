package webserver;

import db.Database;
import java.util.HashMap;
import java.util.Map;
import model.User;

public class HttpRequest {
    private String method;
    private String path;
    private String version;
    private Map<String, String> queryMap = new HashMap<String, String>();

    public HttpRequest(String requestLine) {
        String[] split = requestLine.split(" ");
        this.method = split[0];
        this.path = split[1];
        this.version = split[2];
    }

    public User createUser() {
        String queryParameter = getTokens(path, "\\?")[1];
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
        return path;
    }
}
