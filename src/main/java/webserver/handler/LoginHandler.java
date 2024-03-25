package webserver.handler;

import db.Database;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import java.util.Map;
import java.util.UUID;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginHandler implements RequestHandler {
    private final Logger logger = LoggerFactory.getLogger(LoginHandler.class);

    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        User user = Database.findUserById(userId);
        logger.debug("User: " + user);

        if (user.getUserId().equals(userId) && user.getPassword().equals(password)) {
            String cookie = String.format("sid=%s;", UUID.randomUUID().toString());
            httpResponse.addCookie(cookie);
            httpResponse.addStatus(HttpStatus.FOUND);
            httpResponse.addLocation("/index.html");
        }
        
    }
}
