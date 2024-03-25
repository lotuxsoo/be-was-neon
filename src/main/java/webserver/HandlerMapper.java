package webserver;

import java.util.HashMap;
import java.util.Map;
import webserver.handler.CreateHandler;
import webserver.handler.HomeHandler;
import webserver.handler.LoginHandler;
import webserver.handler.RequestHandler;
import webserver.handler.StaticFileHandler;

public class HandlerMapper {
    private final Map<String, RequestHandler> map = new HashMap<>() {{
        put("/", new HomeHandler());
        put("/index.html", new StaticFileHandler());
        put("/user/create", new CreateHandler());
        put("/user/login", new LoginHandler());
    }};

    public RequestHandler getHandler(String uri) {
        return map.getOrDefault(uri, new StaticFileHandler());
    }
}
