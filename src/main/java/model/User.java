package model;

public class User {
    private String userId;
    private String name;
    private String password;

    public User(String userId, String name, String password) {
        this.userId = userId;
        this.name = name;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }


    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "User [userId=" + userId + ", name=" + name + ", password=" + password + "]";
    }
}
