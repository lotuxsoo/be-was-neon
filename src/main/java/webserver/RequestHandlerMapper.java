package webserver;

import java.util.HashMap;
import java.util.Map;
import webserver.handler.CreateHandler;
import webserver.handler.HomeHandler;
import webserver.handler.LoginHandler;
import webserver.handler.RequestHandler;
import webserver.handler.StaticFileHandler;

public class RequestHandlerMapper {
    private final Map<String, RequestHandler> map = new HashMap<>() {{
        put("/", new HomeHandler());
        put("/index.html", new StaticFileHandler());
        put("/create", new CreateHandler());
        put("/login", new LoginHandler());
    }};

    public RequestHandler getHandler(String uri) {
        return map.getOrDefault(uri, new StaticFileHandler());
    }
}
