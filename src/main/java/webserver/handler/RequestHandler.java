package webserver.handler;

import http.HttpRequest;
import http.HttpResponse;

public interface RequestHandler {
    HttpResponse handle(HttpRequest httpRequest);
}
