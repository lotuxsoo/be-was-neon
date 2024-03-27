package webserver.mapper;

import webserver.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;
import webserver.handler.CreateHandler;
import webserver.handler.LoginHandler;
import webserver.handler.RequestHandler;
import webserver.handler.StaticFileHandler;

public class HandlerMapper {
    private final Map<String, RequestHandler> map = new HashMap<>() {{
        put("/user/create", new CreateHandler());
        put("/user/login", new LoginHandler());
    }};

    public RequestHandler getHandler(HttpRequest httpRequest) {
        return map.getOrDefault(httpRequest.getPath(), new StaticFileHandler());
    }
}