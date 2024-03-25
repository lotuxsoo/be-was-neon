package webserver.handler;

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
    public HttpResponse handle(HttpRequest httpRequest) {
        Map<String, String> queryMap = httpRequest.getQueryMap();
        User user = User.makeUser(queryMap);
        logger.debug(user.toString());

        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setStatus(HttpStatus.FOUND);
        httpResponse.setLocation("/index.html");
        return httpResponse;
    }
}
