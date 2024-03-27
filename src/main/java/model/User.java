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

    public static User makeUser(Map<String, String> paramMap) {
        User user = new User(paramMap.get("userId"), paramMap.get("password"), paramMap.get("name"),
                paramMap.get("email"));
        return user;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
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