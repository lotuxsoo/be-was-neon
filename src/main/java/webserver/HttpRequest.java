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
        String[] tempdb = new String[params.length];
        for (int i = 0; i < params.length; i++) {
            tempdb[i] = getTokens(params[i], "=")[1]; // param[1]이 User 객체의 정보
        }
        User user = new User(tempdb[0], tempdb[1], tempdb[2]); // id, nickname, password 저장
        Database.addUser(user);
        User userById = Database.findUserById(tempdb[0]);
        return userById;
    }

    public String[] getTokens(String str, String separator) {
        return str.split(separator);
    }

    public String getPath() {
        return path;
    }
}
