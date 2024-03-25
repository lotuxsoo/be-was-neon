package webserver.handler;

import http.HttpRequest;
import http.HttpResponse;

public class LoginHandler implements RequestHandler{
    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        HttpResponse httpResponse = new HttpResponse();

        return httpResponse;
    }
}
