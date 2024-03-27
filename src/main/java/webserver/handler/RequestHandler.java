package webserver.handler;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public interface RequestHandler {
    void handle(HttpRequest httpRequest, HttpResponse httpResponse);
}
