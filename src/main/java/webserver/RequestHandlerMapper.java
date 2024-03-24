package webserver;

import java.util.HashMap;
import java.util.Map;
import webserver.handler.CreateHandler;
import webserver.handler.HomeHandler;
import webserver.handler.LoginHandler;
import webserver.handler.RequestHandler;

public class RequestHandlerMapper {
    Map<String, RequestHandler> map = new HashMap<>(){{
        put("/", new HomeHandler());
        put("/create", new CreateHandler());
        put("/login", new LoginHandler());
    }};
}
