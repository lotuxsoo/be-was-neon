package webserver;

import http.HttpRequest;
import http.HttpResponse;
import http.RequestReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.RequestHandler;

public class MainHandler implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(MainHandler.class);
    private final Socket connection;

    public MainHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            RequestReader reader = new RequestReader(in);

            HttpRequest httpRequest = reader.readRequest();

            HandlerMapper handlerMapper = new HandlerMapper();

            RequestHandler handler = handlerMapper.getHandler(httpRequest);

            HttpResponse httpResponse = new HttpResponse();

            handler.handle(httpRequest, httpResponse);

            httpResponse.send(out);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}