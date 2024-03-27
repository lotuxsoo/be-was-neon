package webserver;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.RequestHandler;
import webserver.manager.RequestManager;
import webserver.mapper.HandlerMapper;

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
            RequestManager manager = new RequestManager(in); // HTTP 요청을 읽어서 필요한 형태로 값을 파싱하는 객체
            HttpRequest httpRequest = manager.readRequest(); // RequestManager가 HttpRequest 객체를 생성

            HttpResponse httpResponse = new HttpResponse();

            HandlerMapper handlerMapper = new HandlerMapper();

            RequestHandler handler = handlerMapper.getHandler(httpRequest);
            handler.handle(httpRequest, httpResponse);

            httpResponse.send(out);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}