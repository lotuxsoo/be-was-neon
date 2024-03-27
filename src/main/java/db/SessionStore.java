package db;

import java.util.HashMap;
import java.util.Map;
import model.User;

public class SessionStore {
    private static final Map<String, String> sessions = new HashMap<>();

    public static void addSession(String sid, String userId) {
        sessions.put(sid, userId);
    }

    public static void removeSession(String sid) {
        sessions.remove(sid);
    }

    public static User findUserBySid(String sid) {
        String userId = sessions.get(sid);
        return Database.findUserById(userId);
    }
}
