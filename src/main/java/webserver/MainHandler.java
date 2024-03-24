package webserver;

import http.ContentType;
import http.HttpRequest;
import http.HttpResponse;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.CreateHandler;
import webserver.handler.HomeHandler;
import webserver.handler.LoginHandler;
import webserver.handler.RequestHandler;

public class MainHandler implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Socket connection;

    public MainHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            HttpRequest httpRequest = new HttpRequest(in);
            String method = httpRequest.getMethod();
            String resource = httpRequest.getResource();
            HttpResponse httpResponse = new HttpResponse();

            RequestHandlerMapper handlerMapper = new RequestHandlerMapper();

            RequestHandler handler = handlerMapper.getHandler(resource);
            handler.handle(httpRequest, httpResponse);

            if (method.equals("GET") && resource.endsWith(".html")) {
                // HomeHandler
            } else if (method.equals("POST") && resource.startsWith("/create")) {
                // CreateHandler
                // httpResponse.response302Header(dos);
            } else if (method.equals("POST") && resource.startsWith("/login")) {
                // LoginHandler
                // httpResponse.response302HeaderWithCookie(dos, sessionId);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}