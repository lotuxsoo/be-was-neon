package webserver.handler;

import http.HttpRequest;
import http.HttpResponse;

public interface RequestHandler {
    void handle(HttpRequest request, HttpResponse response);
}
