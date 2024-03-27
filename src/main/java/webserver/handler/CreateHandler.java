package webserver.handler;

import db.Database;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import java.util.Map;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateHandler implements RequestHandler {
    private final Logger logger = LoggerFactory.getLogger(CreateHandler.class);

    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> paramMap = httpRequest.getParamMap();
        User user = User.makeUser(paramMap);
        Database.addUser(user); // db에 유저 생성

        logger.debug("새로운 유저가 생성되었습니다. {}", user);

        httpResponse.setStatus(HttpStatus.FOUND);
        httpResponse.setLocation("/index.html");
    }
}
