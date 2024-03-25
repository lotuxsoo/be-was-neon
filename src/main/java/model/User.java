package model;

import db.Database;
import java.util.Map;

public class User {
    private String userId;
    private String name;
    private String password;
    private String email;

    private User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static User makeUser(Map<String, String> queryMap) {
        User user = new User(queryMap.get("userId"), queryMap.get("password"), queryMap.get("name"),
                queryMap.get("email"));
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