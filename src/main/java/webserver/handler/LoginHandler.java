package webserver.handler;

import db.Database;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import java.util.Map;
import java.util.UUID;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginHandler implements RequestHandler {
    private final Logger logger = LoggerFactory.getLogger(LoginHandler.class);

    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> queryMap = httpRequest.getQueryMap();
        String userId = queryMap.get("userId");
        String password = queryMap.get("password");

        User user = Database.findUserById(userId); // db에서 유저 찾아옴

        if (user == null) {
            //  httpResponse.setStatus(HttpStatus.FOUND);
            httpResponse.setLocation("/index.html");
            logger.debug("[로그인] 해당하는 유저가 db에 없습니다.");
        } else if (user.getUserId().equals(userId) && user.getPassword().equals(password)) {
            String cookie = String.format("sid=%s;", UUID.randomUUID());
            httpResponse.setStatus(HttpStatus.FOUND);
            httpResponse.setCookie(cookie);
            httpResponse.setLocation("/main/index.html");
            logger.debug("[로그인] 로그인이 성공했습니다. {}", user);
        } else {
            //     httpResponse.setStatus(HttpStatus.FOUND);
            httpResponse.setLocation("/login/failed.html");
            logger.debug("[로그인] 로그인에 실패했습니다.");
        }
    }
}