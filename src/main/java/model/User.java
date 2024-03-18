package model;

import static utils.StringUtil.getTokens;

import db.Database;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String userId;
    private String name;
    private String password;
    private String email;

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static User from(String queryParameter) {
        Map<String, String> map = new HashMap<String, String>();

        String[] params = getTokens(queryParameter, "&");

        for (int i = 0; i < params.length; i++) {
            String[] tokens = getTokens(params[i], "=");
            map.put(tokens[0], tokens[1]);
        }

        User user = new User(map.get("userId"), map.get("password"), map.get("name"),
                map.get("email"));
        Database.addUser(user);
        return Database.findUserById(user.getUserId());
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "User {" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}